package com.springboot.activemq.producer;

import javax.jms.Queue;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service("producer")
@Slf4j
public class Producer {
    public static final String DEFAULT_QUEUE = "mine.msg.queue";
    public static final String OUT_QUEUE = "mine.out.queue";
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    private Queue queue;

    public void sendMessage(final String message) {
        if(queue == null){
            queue = new ActiveMQQueue(DEFAULT_QUEUE);
        }
        jmsTemplate.convertAndSend(queue, message);
    }

    public void sendMessage(final String message,final String queueName) {
        if(queue == null){
            queue = new ActiveMQQueue(StringUtils.isNotBlank(queueName)?queueName:DEFAULT_QUEUE);
        }
        jmsTemplate.convertAndSend(queue, message);
    }

    @JmsListener(destination = OUT_QUEUE)
    public void consumerMessage(String text) {
        log.info("从[{}]队列收到的回复报文为:{}",OUT_QUEUE,text);
    }
}