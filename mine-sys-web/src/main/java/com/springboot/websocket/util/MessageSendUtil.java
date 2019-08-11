package com.springboot.websocket.util;

import com.springboot.common.business.SpringContextHolder;
import com.springboot.common.util.LoginPersonUtil;
import com.springboot.websocket.disruptor.IDisruptorQueue;
import com.springboot.websocket.disruptor.entity.Message;

import java.text.DateFormat;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author wangshibao<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2019/08/04 17:42 <br>
 * @see com.springboot.websocket.util <br>
 */
public class MessageSendUtil {

    private static IDisruptorQueue disruptorQueue = SpringContextHolder.getBean("disruptorQueue");

    /**
     * 发送广播消息
     */
    public static void sendTopicMessage(Message message) {
        message.setLevel("topic");
        message.setClassName(MessageSendUtil.class.getName());
        message.setTimestamp(DateFormat.getDateTimeInstance().format(new Date()));
        message.setThreadName(Thread.currentThread().getName());
        message.setUserId(LoginPersonUtil.getUserId());
        message.setOrganId(LoginPersonUtil.getOrganId());
        sendMessage(message);
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public static void sendMessage(Message message) {
        disruptorQueue.publishEvent(message);
    }
}
