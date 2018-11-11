package com.springboot.websocket.disruptor;

import com.springboot.websocket.LoggerMessage;

/**
 * Content :进程日志事件内容载体
 */
public class LoggerEvent {

    private LoggerMessage log;

    public LoggerMessage getLog() {
        return log;
    }

    public void setLog(LoggerMessage log) {
        this.log = log;
    }
}
