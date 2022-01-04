package com.java.nie.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    // 队列名
    public static final String FANOUT_QUEUE_NAME = "fanout_queue";
    //交换机名
    public static final String TEST_FANOUT_EXCHANGE = "fanout_exchange";

    public static final String DIRECT_QUEUE_NAME = "direct_queue";
    public static final String TEST_DIRECT_EXCHANGE = "direct_exchange";
    public static final String DIRECT_ROUTINGKEY = "test";
    // 创建队列
    @Bean
    public Queue createFanoutQueue() {
        return new Queue(FANOUT_QUEUE_NAME);
    }

    @Bean
    public Queue createDirectQueue() {
        return new Queue(DIRECT_QUEUE_NAME);
    }

    // 创建交换机
    @Bean
    public FanoutExchange defFanoutExchange() {
        return new FanoutExchange(TEST_FANOUT_EXCHANGE);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(TEST_DIRECT_EXCHANGE);
    }

    // 队列与交换机进行绑定
    @Bean
    Binding bindingFanout() {
        return BindingBuilder.bind(createFanoutQueue()).to(defFanoutExchange());
    }

    //队列与交换机绑定并添加路由key（direct和topic模式）
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(createDirectQueue()).to(directExchange()).with(DIRECT_ROUTINGKEY);
    }
}
