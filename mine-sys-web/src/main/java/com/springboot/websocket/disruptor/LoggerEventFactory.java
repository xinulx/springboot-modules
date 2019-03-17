package com.springboot.websocket.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Content :进程日志事件工厂类
 */
public class LoggerEventFactory implements EventFactory<LoggerEvent> {
    @Override
    public LoggerEvent newInstance() {
        return new LoggerEvent();
    }
}
