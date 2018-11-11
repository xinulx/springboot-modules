package com.springboot.activemq.consumer;

import com.springboot.activemq.producer.Producer;
import com.springboot.common.busi.BaseRunTimeException;
import com.springboot.websocket.LoggerDisruptorQueue;
import com.springboot.websocket.LoggerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class Consumer {

    public static final ThreadLocal<Map<Integer, String>> threadLocal = new ThreadLocal<>();

    private AtomicInteger integer = new AtomicInteger(0);

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = Producer.DEFAULT_QUEUE)
    @SendTo(Producer.OUT_QUEUE) // 将返回值再发送到队列中
    public void receiveQueue(String text) {
        log.info("Consumer收到的报文为:{}", text);
        Map<Integer, String> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
        }
        Integer key = integer.incrementAndGet();
        map.put(key, text);
        threadLocal.set(map);
        // 直接推送
        LoggerMessage loggerMessage = new LoggerMessage(1, text,
                DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())),
                Thread.currentThread().getName(),
                this.getClass().getName(),
                this.getClass().getTypeName()
        );
        final LoggerMessage message = loggerMessage;
        cachedThreadPool.execute(() ->
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new BaseRunTimeException("推送消息发生错误:"+e.getMessage());
            }
            LoggerDisruptorQueue.publishMQ(message);
        });
    }
}