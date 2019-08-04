package com.springboot.websocket.disruptor;

import com.springboot.websocket.disruptor.entity.Message;

/**
 * 环形队列接口
 */
public interface IDisruptorQueue {

    /**
     * 发送消息
     * @param message
     */
    public void publishEvent(Message message);
}
