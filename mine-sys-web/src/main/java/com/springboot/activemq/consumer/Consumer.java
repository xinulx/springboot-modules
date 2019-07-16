package com.springboot.activemq.consumer;

import com.springboot.activemq.producer.Producer;
import com.springboot.websocket.LoggerDisruptorQueue;
import com.springboot.websocket.LoggerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用java自带线程池进行异步消息推送
 * a. 重用存在的线程，减少对象创建、消亡的开销，性能佳
 * b. 可有效控制最大并发线程数，提高系统资源的使用率，同时避免过多资源竞争，避免堵塞
 * c. 提供定时执行、定期执行、单线程、并发数控制等功能
 */
@Component
@Slf4j
public class Consumer {
    /**
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
     * 线程池为无限大
     * 当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
     */
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = Producer.DEFAULT_QUEUE)
    @SendTo(Producer.OUT_QUEUE) // 将返回值再发送到队列中
    public void receiveQueue(String text) {
        log.info("Consumer收到的报文为:{}", text);
        // 直接推送
        final LoggerMessage message = new LoggerMessage(1, text, DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())), Thread.currentThread().getName(), this.getClass().getName(), this.getClass().getTypeName());
        cachedThreadPool.execute(() -> LoggerDisruptorQueue.publishMQ(message));
    }
}