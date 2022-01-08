package com.java.nie.controller;


import com.java.nie.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test1")
    public void  test1() {
        String msg ="测试topic1是否可以消费info" ;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO,RabbitMQConfig.ROUTING_TOPIC_DEMO_ONE ,msg);
    }

    @GetMapping("/test2")
    public void  test2() {
        String msg ="测试topic2是否可以消费warning" ;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO,RabbitMQConfig.ROUTING_TOPIC_DEMO_TWO,msg);
    }

    @GetMapping("/test3")
    public void  test3() {
        String msg ="测试topic2是否可以消费warning" ;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO,RabbitMQConfig.ROUTING_TOPIC_DEMO_THREE,msg);
    }

    @GetMapping("/dddemo")
    public void demo(String id) {
        //指定延时队列交换机   延时队列路由  和  设置消息过期时间
        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_DELAY_EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DELAY, id, mes -> {
            mes.getMessageProperties().setExpiration(1000 * 20 + "");
            return mes;
        });
    }


}
