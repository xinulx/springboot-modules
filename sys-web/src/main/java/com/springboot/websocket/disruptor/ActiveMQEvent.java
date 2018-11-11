package com.springboot.websocket.disruptor;

import com.springboot.websocket.LoggerMessage;

/**
 * Content :消息发送事件
 */
public class ActiveMQEvent {
    private LoggerMessage message;

    public LoggerMessage getMessage() {
        return message;
    }

    public void setMessage(LoggerMessage message) {
        this.message = message;
    }
}
