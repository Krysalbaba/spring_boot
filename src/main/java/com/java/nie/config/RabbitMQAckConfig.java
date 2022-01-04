package com.java.nie.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@Configuration
public class RabbitMQAckConfig implements RabbitTemplate.ConfirmCallback{

    private final static Logger log = LoggerFactory.getLogger(RabbitMQAckConfig.class);

    @Autowired
    private RabbitTemplate rabbitTemplate ;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);            // 指定 ConfirmCallback
    }

    /**
     * 如果消息到达 exchange, 则 confirm 回调, ack = true
     * 如果消息不到达 exchange, 则 confirm 回调, ack = false
     * 需要设置spring.rabbitmq.publisher-confirms=true
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息是否到达Exchange:{}", ack == true ? "消息成功到达Exchange" : "消息到达Exchange失败");
        if (!ack) {
            log.info("消息到达Exchange失败原因:{}", cause);
            // 根据业务逻辑实现消息补偿机制
        }
    }
}
