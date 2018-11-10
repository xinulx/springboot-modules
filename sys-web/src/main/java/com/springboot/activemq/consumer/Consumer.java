package com.springboot.activemq.consumer;

import com.springboot.activemq.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    // 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
    @JmsListener(destination = Producer.DEFAULT_QUEUE)
    @SendTo(Producer.OUT_QUEUE) // 将返回值再发送到队列中
    public void receiveQueue(String text) {
        log.info("Consumer收到的报文为:{}",text);
    }
}