package com.springboot.activemq.producer;

import javax.jms.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service("producer")
@Slf4j
public class Producer {
    public static final String DEFAULT_QUEUE = "mine.msg.queue";
    public static final String OUT_QUEUE = "mine.out.queue";

    public static final Destination DEFAULT_QUEUE_OBJECT = new ActiveMQQueue("default.queue");

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /** 消息队列 */
    private Queue queue;

    /**
     * 发送消息，如果队列还没有创建则创建一个
     * @param message
     */
    public void sendMessage(final String message) {
        log.info("开始向[{}]队列推送消息",DEFAULT_QUEUE);
        if(queue == null){
            queue = new ActiveMQQueue(DEFAULT_QUEUE);
        }
        jmsTemplate.convertAndSend(queue, message);
    }

    /**
     * 发送消息，如果队列还没有创建则创建一个，可以指定队列名称
     * @param message
     */
    public void sendMessage(final String message,final String queueName) {
        log.info("开始向[{}]队列推送消息",DEFAULT_QUEUE);
        if(queue == null){
            queue = new ActiveMQQueue(StringUtils.isNotBlank(queueName)?queueName:DEFAULT_QUEUE);
        }
        jmsTemplate.convertAndSend(queue, message);
    }

    /**
     * 监听消费者的回复信息
     * @param text 消费者向生产者回复的消息报文（json字符串）
     */
    @JmsListener(destination = OUT_QUEUE)
    public void consumerMessage(String text) {
        log.info("从[{}]队列收到的回复报文为:{}",OUT_QUEUE,text);
    }

    /**
     * 发送消息,支持任何序列化对象
     * @param destination destination是发送到的队列
     * @param message message是待发送的消息
     */
    public <T extends Serializable> void send(Destination destination, T message){
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 延时发送
     * @param destination 发送的队列
     * @param data 发送的消息
     * @param time 延迟时间
     */
    public <T extends Serializable> void delaySend(Destination destination, T data, Long time){
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        // 获取连接工厂
        ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
        try {
            // 获取连接
            connection = connectionFactory.createConnection();
            connection.start();
            // 获取session，true开启事务，false关闭事务
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            producer = session.createProducer(destination);
            producer.setDeliveryMode(JmsProperties.DeliveryMode.PERSISTENT.getValue());
            ObjectMessage message = session.createObjectMessage(data);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            // 发送消息
            producer.send(message);
            session.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (producer != null){
                    producer.close();
                }
                if (session != null){
                    session.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}