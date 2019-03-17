package com.springboot.websocket.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Content :文件日志事件工厂类
 */
public class FileLoggerEventFactory implements EventFactory<FileLoggerEvent> {
    @Override
    public FileLoggerEvent newInstance() {
        return new FileLoggerEvent();
    }
}
