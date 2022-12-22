package com.java.nie.controller;


import com.java.nie.utils.RedisDelayQueueUtil;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/http")
public class HttpController {


    @Autowired
    private RestTemplate restTemplate ;

    @Autowired
    private RedissonClient redissonClient ;

    @Autowired
    private RedisDelayQueueUtil redisDelayQueueUtil ;

    @GetMapping("/httpDemo")
    public void httpDemo(){
        String url = "http://localhost:9090/tUser/checkLoginName/zhangsan";
        ResponseEntity<String> result2 = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), String.class);
        String jsonStr = result2.getBody();

    }

    @GetMapping("/delay/{time}")
    public void delay(@PathVariable("time")Integer time){
        redisDelayQueueUtil.addDelayQueue("测试消息",time, TimeUnit.SECONDS,"ORDER_TIMEOUT_NOT_EVALUATED");

    }


}
