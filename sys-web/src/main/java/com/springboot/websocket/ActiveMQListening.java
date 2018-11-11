package com.springboot.websocket;

import com.springboot.activemq.consumer.Consumer;
import com.springboot.common.busi.BaseRunTimeException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ActiveMQListening implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try{
            // 启动MQ消息监听
            Map<Integer, String> map = Consumer.threadLocal.get();
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
            exec.scheduleWithFixedDelay(() -> {
                while (true) {
                }
            }, 0, 1, TimeUnit.SECONDS);
        }catch (BaseRunTimeException e){
            e.printStackTrace();
        }
    }
}
