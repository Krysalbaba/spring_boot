package com.java.nie.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    /**
     *  延时队列创建  开始
     */


    // 普通队列名
    public static final String QUEUE_NAME = "queue.name";
    // 普通交换机名
    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange.name";
    // 普通路由key
    public static final String ROUTING_KEY = "routing_key";
    //延时队列名
    public static final String DELAY_QUEUE_NAME = "delay.queue.name";
    //延时交换机名
    public static final String DIRECT_DELAY_EXCHANGE_NAME = "direct.delay.exchange.name";
    //延时路由key
    public static final String ROUTING_KEY_DELAY = "routing_key_delay";

    //创建普通队列
    @Bean
    public Queue createQueue(){
        return new Queue(QUEUE_NAME,true);
    }

    //创建延时队列
    @Bean
    public Queue createDelayQueue(){
        Map<String,Object>  map =new HashMap<>(3);
        //声明转发时的交换机名
        map.put("x-dead-letter-exchange",TOPIC_EXCHANGE_NAME);
        //声明转发时的路由key
        map.put("x-dead-letter-routing-key",ROUTING_KEY);
        //创建延时队列
        return  new Queue(DELAY_QUEUE_NAME,true,false,false,map) ;
    }

    //创建普通交换机
    @Bean
    public TopicExchange createTopicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    //创建延时交换机
    @Bean
    public DirectExchange createDirectExchange(){
        return new DirectExchange(DIRECT_DELAY_EXCHANGE_NAME);
    }


    //延时队列绑定
    @Bean
    public Binding dlxBinding(){
        return BindingBuilder.bind(createDelayQueue()).to(createDirectExchange()).with(ROUTING_KEY_DELAY);
    }

    //普通队列绑定
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(createQueue()).to(createTopicExchange()).with(ROUTING_KEY);
    }


    /**
     *  延时队列创建 结束
     */



    //试用 direct 交换机
    public static final  String QUEUE_DIRECT_DEMO_ONE="queue.direct.demo.one";
    public static final  String QUEUE_DIRECT_DEMO_TWO="queue.direct.demo.one";
    public static final  String EXCHANGE_DIRECT_DEMO="exchange.direct.demo";
    public static final  String ROUTING_DIRECT_DEMO_ONE="routing.direct.demo.one";
    public static final  String ROUTING_DIRECT_DEMO_TWO="routing.direct.demo.two";

    @Bean
    public Queue createDirectOne(){
        return new Queue(QUEUE_DIRECT_DEMO_ONE,false);
    }

    @Bean
    public Queue createDirectTwo(){
        return new Queue(QUEUE_DIRECT_DEMO_TWO,false);
    }

    @Bean
    public DirectExchange createDirectExchangeDemo(){
        return new DirectExchange(EXCHANGE_DIRECT_DEMO);
    }

    @Bean
    public Binding bindDirectOne(){
        return BindingBuilder.bind(createDirectOne()).to(createDirectExchangeDemo()).with(ROUTING_DIRECT_DEMO_ONE);
    }

    @Bean
    public Binding bindDirectTwo(){
        return BindingBuilder.bind(createDirectTwo()).to(createDirectExchangeDemo()).with(ROUTING_DIRECT_DEMO_TWO);
    }
}
