package com.java.nie.controller;


import com.java.nie.utils.RedisDelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
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

/**
 * @author nie
 * @date 2023-02-17
 * @apiNote httpclient调用测试
 */
@RestController
@RequestMapping("/http")
@Slf4j
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

        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque("ORDER_TIMEOUT_NOT_EVALUATED");
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer("测试消息", time, TimeUnit.SECONDS);
        //delayedQueue.destroy();
        log.info("(添加延时队列成功) 队列键：{}，队列值：{}，延迟时间：{}", "ORDER_TIMEOUT_NOT_EVALUATED", "测试消息", TimeUnit.SECONDS.toSeconds(time) + "秒");

    }


}
