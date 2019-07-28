package com.springboot.websocket;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.springboot.websocket.disruptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Content :Disruptor 环形队列
 */
@Component
public class DisruptorQueue {
    /**
     * 缓冲线程池
     */
    private Executor executor = Executors.newCachedThreadPool();

    /**
     * 创建队列事件的工厂对象
     */
    private LoggerEventFactory loggerEventFactory = new LoggerEventFactory();
    private ActiveMQEventFactory activeMQEventFactory = new ActiveMQEventFactory();
    private FileLoggerEventFactory fileLoggerEventFactory = new FileLoggerEventFactory();

    /**
     * 指定循环缓冲区的大小,必须是2的幂
     */
    private int bufferSize = 2 * 1024;

    /**
     * 构建日志循环队列
     */
    private Disruptor<LoggerEvent> disruptor = new Disruptor<>(loggerEventFactory, bufferSize, executor);

    /**
     * 构建消息循环队列
     */
    private Disruptor<ActiveMQEvent> activeDisruptor = new Disruptor<>(activeMQEventFactory, bufferSize, executor);

    /**
     * 构建文件日志循环队列
     */
    private Disruptor<FileLoggerEvent> fileLoggerEventDisruptor = new Disruptor<>(fileLoggerEventFactory, bufferSize, executor);

    private static RingBuffer<LoggerEvent> ringBuffer;

    private static RingBuffer<ActiveMQEvent> activeMQEventRingBuffer;

    private static RingBuffer<FileLoggerEvent> fileLoggerEventRingBuffer;

    @Autowired
    private DisruptorQueue(LoggerEventHandler eventHandler, FileLoggerEventHandler fileLoggerEventHandler, ActiveMQEventHandler activeMQEventHandler, UserActiveMQEventHandler userActiveMQEventHandler) {
        disruptor.handleEventsWith(eventHandler);
        activeDisruptor.handleEventsWith(activeMQEventHandler, userActiveMQEventHandler);
        fileLoggerEventDisruptor.handleEventsWith(fileLoggerEventHandler);

        ringBuffer = disruptor.getRingBuffer();
        activeMQEventRingBuffer = activeDisruptor.getRingBuffer();
        fileLoggerEventRingBuffer = fileLoggerEventDisruptor.getRingBuffer();

        disruptor.start();
        activeDisruptor.start();
        fileLoggerEventDisruptor.start();
    }

    public static void publishEvent(Message log) {
        long sequence = ringBuffer.next();
        try {
            LoggerEvent event = ringBuffer.get(sequence);
            event.setLog(log);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    /**
     * 消息推送
     */
    public static void publishMQ(Message message) {
        if (activeMQEventRingBuffer == null) return;
        long sequence = activeMQEventRingBuffer.next();
        try {
            ActiveMQEvent event = activeMQEventRingBuffer.get(sequence);
            if (message == null) {
                message = new Message();
            }
            event.setMessage(message);
        } finally {
            activeMQEventRingBuffer.publish(sequence);
        }
    }

    public static void publishEvent(String log) {
        if (fileLoggerEventRingBuffer == null) return;
        long sequence = fileLoggerEventRingBuffer.next();
        try {
            FileLoggerEvent event = fileLoggerEventRingBuffer.get(sequence);
            event.setLog(log);
        } finally {
            fileLoggerEventRingBuffer.publish(sequence);
        }
    }
}
