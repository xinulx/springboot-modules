package com.springboot.websocket.disruptor.impl;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.springboot.common.busi.BaseRunTimeException;
import com.springboot.websocket.disruptor.IDisruptorQueue;
import com.springboot.websocket.disruptor.entity.Message;
import com.springboot.websocket.disruptor.entity.MessageEvent;
import com.springboot.websocket.disruptor.factory.TopicEventFactory;
import com.springboot.websocket.disruptor.handler.TopicEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Content :Disruptor 环形队列实现类
 */
@Component("disruptorQueue")
public class DisruptorQueueImpl implements IDisruptorQueue {

    private Logger logger = LoggerFactory.getLogger("topicMessage");
    /**
     * 缓冲线程池
     */
    private Executor executor = Executors.newCachedThreadPool();

    /**
     * 创建队列事件的工厂对象
     */
    private TopicEventFactory loggerEventFactory = new TopicEventFactory();

    /**
     * 指定循环缓冲区的大小,必须是2的幂
     */
    private int bufferSize = 2 * 1024;

    /**
     * 构建消息循环队列
     */
    private Disruptor<MessageEvent> disruptor = new Disruptor<>(loggerEventFactory, bufferSize, executor);

    /**
     * 缓冲数据环：不同上下文（线程）间传递的数据的buffer
     * 程序运行时会不断创建索引序号，并填充buffer(直到绕过这个环)
     * 索引个数最好指定2的幂次有利于二进制计算
     */
    private static RingBuffer<MessageEvent> ringBuffer;

    @Autowired
    private DisruptorQueueImpl(TopicEventHandler eventHandler) {
        disruptor.handleEventsWith(eventHandler);
        ringBuffer = disruptor.getRingBuffer();
        disruptor.start();
    }

    /**
     * 消息发送方法
     *
     * @param message
     */
    public void publishEvent(Message message) {
        if (ringBuffer == null) return;
        // 获取缓冲数据环的索引号
        long sequence = ringBuffer.next();
        try {
            // 根据索引序号获取对应元素
            MessageEvent event = ringBuffer.get(sequence);
            event.setMessage(message);
        } catch (BaseRunTimeException e) {
            logger.info("从环形缓冲数据中获取数据出错", e);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
