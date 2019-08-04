package com.springboot.websocket.disruptor.handler;


import com.lmax.disruptor.EventHandler;
import com.springboot.websocket.disruptor.entity.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Content :进程日志事件处理器
 */
@Component
public class TopicEventHandler implements EventHandler<MessageEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onEvent(MessageEvent event, long l, boolean b) {
        messagingTemplate.convertAndSend("/topic/receiveMessage",event.getMessage());
    }
}
