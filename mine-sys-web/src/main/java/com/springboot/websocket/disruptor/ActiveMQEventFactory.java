package com.springboot.websocket.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Content :消息发送工厂类
 */
public class ActiveMQEventFactory implements EventFactory<ActiveMQEvent> {
    @Override
    public ActiveMQEvent newInstance() {
        return new ActiveMQEvent();
    }
}
