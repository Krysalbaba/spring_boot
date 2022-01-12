package com.java.nie.controller;


import com.java.nie.config.RabbitMQConfig;
import com.java.nie.config.RedisService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

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

    @GetMapping("/test4")
    public void  test4() {
        String msg ="测试headers 是否可以消费4" ;
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_HEADERS_DEMO,null,msg,message -> {
            message.getMessageProperties().setHeader("age",20);
            message.getMessageProperties().setHeader("isDel",true);
            message.getMessageProperties().setHeader("sex",1);
            return message;
        });
    }


    @GetMapping("/dddemo")
    public void demo(String id) {
        //指定延时队列交换机   延时队列路由  和  设置消息过期时间
//        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_DELAY_EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DELAY, id, mes -> {
//            mes.getMessageProperties().setExpiration(1000 * 20 + "");
//            return mes;
//        });

        /**
         * redis 测试
         */
        Map<String,Object> map =new HashMap<>();
        map.put("age",20);
        map.put("sex","男");
        redisService.hmset("set",map);
        redisService.hset("set","demo","test");

    }


    @GetMapping("/get")
    public void get() {
        Map<Object, Object> demo = redisService.hmget("set");
        System.err.println("demo----------->"+demo);
        Object age = redisService.hget("demo", "age");
        System.err.println("age----------->"+age);
        Object sex = redisService.hget("demo", "sex");
        System.err.println("sex----------->"+sex);

    }

}
