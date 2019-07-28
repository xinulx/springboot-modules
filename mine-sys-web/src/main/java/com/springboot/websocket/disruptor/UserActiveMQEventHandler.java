package com.springboot.websocket.disruptor;

import com.lmax.disruptor.EventHandler;
import com.springboot.common.util.AppUtil;
import com.springboot.common.util.LoginPersonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Content :广播消息处理器
 */
@Component
public class UserActiveMQEventHandler implements EventHandler<ActiveMQEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onEvent(ActiveMQEvent activeMQEvent, long l, boolean b) {
        Long userId = LoginPersonUtil.getUserId();
        if (!AppUtil.isEmpty(userId)) {
            messagingTemplate.convertAndSendToUser(userId.toString(), "/user/receiveMessage", activeMQEvent.getMessage());
        }
    }
}
