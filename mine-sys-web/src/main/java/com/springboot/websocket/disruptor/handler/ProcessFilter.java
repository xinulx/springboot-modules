package com.springboot.websocket.disruptor.handler;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.websocket.disruptor.IDisruptorQueue;
import com.springboot.websocket.disruptor.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;

/**
 * 监听系统日志并推送到前端
 */
@Service
public class ProcessFilter extends Filter<ILoggingEvent> {

    @Autowired
    private IDisruptorQueue disruptorQueue;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        Message message = new Message();
        message.setTitle("系统日志");
        message.setBody(event.getMessage());
        message.setLevel(event.getLevel().levelStr);
        message.setStatus(Message.Status.success.getValue());
        message.setClassName(event.getLoggerName());
        message.setTimestamp(DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())));
        message.setThreadName(event.getThreadName());
        message.setType(Message.Type.log.name());
        message.setUserId(LoginPersonUtil.getUserId());
        message.setOrganId(LoginPersonUtil.getOrganId());
        disruptorQueue.publishEvent(message);
        return FilterReply.ACCEPT;
    }
}  