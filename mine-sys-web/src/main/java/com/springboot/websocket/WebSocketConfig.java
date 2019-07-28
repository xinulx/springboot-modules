package com.springboot.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * 注解开启STOMP协议来传输基于代理的消息，此时控制器支持使用
 */
@Configuration
@EnableWebSocketMessageBroker
@MessageMapping
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //topic用来广播，user用来实现p2p
        config.enableSimpleBroker("/topic", "/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个普通的通信连接并允许跨域，用于广播
        registry.addEndpoint("/websocket").setAllowedOrigins("http://127.0.0.1:8816").withSockJS();
        // 注册一个点对点的通信连接并放入拦截器，用于点对点通信并受权限控制
        registry.addEndpoint("/userSocket").addInterceptors().withSockJS();
    }
}