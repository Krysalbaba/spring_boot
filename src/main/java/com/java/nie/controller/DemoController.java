package com.java.nie.controller;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.util.DateUtils;
import com.java.nie.config.RabbitMQConfig;
import com.java.nie.config.RedisService;
import com.java.nie.utils.DateCalculationUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test1")
    public void test1() {
        String msg = "测试topic1是否可以消费info";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO, RabbitMQConfig.ROUTING_TOPIC_DEMO_ONE, msg);
    }

    @GetMapping("/test2")
    public void test2() {
        String msg = "测试topic2是否可以消费warning";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO, RabbitMQConfig.ROUTING_TOPIC_DEMO_TWO, msg);
    }

    @GetMapping("/test3")
    public void test3() {
        String msg = "测试topic2是否可以消费warning";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC_DEMO, RabbitMQConfig.ROUTING_TOPIC_DEMO_THREE, msg);
    }

    @GetMapping("/test4")
    public void test4() {
        String msg = "测试headers 是否可以消费4";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_HEADERS_DEMO, null, msg, message -> {
            message.getMessageProperties().setHeader("age", 20);
            message.getMessageProperties().setHeader("isDel", true);
            message.getMessageProperties().setHeader("sex", 1);
            return message;
        });
    }

    @GetMapping("/test5")
    public void test5() {
        String msg = "测试fanout是否可以消费";
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_FANOUT_DEMO,"", msg);
    }


    private  final static String  PTC_COUNT= "ptc-count:" ;

    @GetMapping("/between/demo")
    public void demo1() throws ParseException {
        List<Map<String,Object>> result = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(PTC_COUNT + "*");
        for (String key :keys ){
            Map<String,Object>  info=new HashMap<>();
            Map<Object, Object> paramMap = redisService.hmget(key);
            Object startTime = paramMap.get("startTime");
            Object endTime = paramMap.get("endTime");
            Long between = between(startTime.toString(), endTime.toString());
            String[] split = key.split(":");
            info.put("between",between);
            info.put("ptcId",split[1]);
            result.add(info);
        }

        result.sort(Comparator.comparing(o ->Long.parseLong(o.get("between").toString())));
        System.out.println(result);
    }

    public Long between(String startTime , String endTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate3 = simpleFormat.parse(startTime);
        Date toDate3 = simpleFormat.parse(endTime);
        long from3 = fromDate3.getTime();
        long to3 = toDate3.getTime();
        long s = ((to3 - from3) / 1000 );
        return s ;
    }

}
