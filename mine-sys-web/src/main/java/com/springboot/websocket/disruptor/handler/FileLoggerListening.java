package com.springboot.websocket.disruptor.handler;

import com.springboot.websocket.disruptor.entity.Message;
import com.springboot.websocket.util.MessageSendUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 文件日志监听器
 * 根据记录上次文件位置来查找有无新的日志信息
 * 并推送到前端
 */
@Service
public class FileLoggerListening implements ApplicationContextAware {

    /**
     * 上次文件大小
     */
    private long lastTimeFileSize = 0;

    @Autowired
    Environment environment;

    /**
     * 消息体
     */
    private static Message message = new Message();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String logPath = environment.getProperty("logging.file");
        try {
            File logFile = ResourceUtils.getFile(logPath);
            final RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
            //指定文件可读可写
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
            exec.scheduleWithFixedDelay(() -> {
                try {
                    randomFile.seek(lastTimeFileSize);
                    String tmp;
                    while ((tmp = randomFile.readLine()) != null) {
                        String log = new String(tmp.getBytes("ISO8859-1"));
                        message.setTitle("读取文件日志");
                        message.setType(Message.Type.fileLog.name());
                        message.setBody(log);
                        MessageSendUtil.sendTopicMessage(message);
                    }
                    lastTimeFileSize = randomFile.length();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, 1, TimeUnit.SECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 监听日志文件
     */
    @PostConstruct
    public void start() {
        System.out.println("开始写入日志");
    }
}