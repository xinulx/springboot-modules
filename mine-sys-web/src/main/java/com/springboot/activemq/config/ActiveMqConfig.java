package com.springboot.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 从ActiveMQ5.12.2 开始，为了增强这个框架的安全性
 * ActiveMQ将强制用户配置可序列化的包名
 */
@Component
public class ActiveMqConfig {

    @Bean
    public ActiveMQConnectionFactory factory(@Value("${spring.activemq.broker-url}") String url) {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        // 设置信任序列化包集合
        List<String> models = new ArrayList<>();
        models.add("java.lang");
        models.add("java.util");
        models.add("com.springboot.mine.activemq");
        factory.setTrustedPackages(models);
        // 设置处理机制
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(0); // 消息处理失败重新处理次数
        factory.setRedeliveryPolicy(redeliveryPolicy);
        return factory;
    }
}