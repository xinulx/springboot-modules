package com.springboot.websocket.disruptor;

import com.springboot.websocket.Message;

/**
 * Content :进程日志事件内容载体
 */
public class LoggerEvent {

    private Message log;

    public Message getLog() {
        return log;
    }

    public void setLog(Message log) {
        this.log = log;
    }
}
