package com.springboot.websocket.disruptor;

import com.springboot.websocket.Message;

/**
 * Content :消息发送事件对象
 */
public class ActiveMQEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
