package com.springboot.websocket.disruptor.entity;

/**
 * Content :消息事件内容载体
 */
public class MessageEvent {

    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
