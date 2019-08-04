package com.springboot.websocket.disruptor.factory;

import com.lmax.disruptor.EventFactory;
import com.springboot.websocket.disruptor.entity.MessageEvent;

/**
 * Content :广播消息事件工厂类
 */
public class TopicEventFactory implements EventFactory<MessageEvent> {
    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }
}
