package com.springboot.websocket;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.springboot.activemq.consumer.Consumer;
import com.springboot.websocket.disruptor.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Content :Disruptor 环形队列
 */
@Component
public class LoggerDisruptorQueue {

    private Executor executor = Executors.newCachedThreadPool();

    // The factory for the event
    private LoggerEventFactory factory = new LoggerEventFactory();

    private ActiveMQEventFactory activeMQEventFactory = new ActiveMQEventFactory();

    private FileLoggerEventFactory fileLoggerEventFactory = new FileLoggerEventFactory();

    // Specify the size of the ring buffer, must be power of 2.
    private int bufferSize = 2 * 1024;

    // Construct the Disruptor
    private Disruptor<LoggerEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);

    private Disruptor<ActiveMQEvent> activeDisruptor = new Disruptor<>(activeMQEventFactory, bufferSize, executor);

    private Disruptor<FileLoggerEvent> fileLoggerEventDisruptor = new Disruptor<>(fileLoggerEventFactory, bufferSize, executor);

    private static RingBuffer<LoggerEvent> ringBuffer;

    private static RingBuffer<ActiveMQEvent> activeMQEventRingBuffer;

    private static RingBuffer<FileLoggerEvent> fileLoggerEventRingBuffer;

    @Autowired
    LoggerDisruptorQueue(LoggerEventHandler eventHandler, FileLoggerEventHandler fileLoggerEventHandler,ActiveMQEventHandler activeMQEventHandler) {
        disruptor.handleEventsWith(eventHandler);
        activeDisruptor.handleEventsWith(activeMQEventHandler);
        fileLoggerEventDisruptor.handleEventsWith(fileLoggerEventHandler);

        this.ringBuffer = disruptor.getRingBuffer();
        this.activeMQEventRingBuffer = activeDisruptor.getRingBuffer();
        this.fileLoggerEventRingBuffer = fileLoggerEventDisruptor.getRingBuffer();

        disruptor.start();
        activeDisruptor.start();
        fileLoggerEventDisruptor.start();
    }

    public static void publishEvent(LoggerMessage log) {
        // Grab the next sequence
        long sequence = ringBuffer.next();
        try {
            LoggerEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setLog(log);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    /**
     * 消息推送
     */
    public static void publishMQ(LoggerMessage message) {
        if (activeMQEventRingBuffer == null) return;
        // Grab the next sequence
        long sequence = activeMQEventRingBuffer.next();
        try {
            ActiveMQEvent event = activeMQEventRingBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            if (message == null) {
                message = new LoggerMessage();
            }
            event.setMessage(message);
        } finally {
            activeMQEventRingBuffer.publish(sequence);
        }
    }

    public static void publishEvent(String log) {
        if (fileLoggerEventRingBuffer == null) return;
        // Grab the next sequence
        long sequence = fileLoggerEventRingBuffer.next();
        try {
            FileLoggerEvent event = fileLoggerEventRingBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setLog(log);
        } finally {
            fileLoggerEventRingBuffer.publish(sequence);
        }
    }
}
