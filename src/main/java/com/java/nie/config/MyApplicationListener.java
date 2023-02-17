package com.java.nie.config;


import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author nie
 * @date 2023-02-17
 *  线程池测试
 */
@Component
@Slf4j
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    int count1 = 0 ;

    int count2 = 0 ;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(2, new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").get(),new ThreadPoolExecutor.AbortPolicy());
        scheduledPool.scheduleAtFixedRate(()->{
            log.info("线程1----->count ->{}",count1++);
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },5,10, TimeUnit.SECONDS);
        scheduledPool.scheduleAtFixedRate(()->{
            log.info("线程2----->count ->{}",count2++);
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },5,10, TimeUnit.SECONDS);
    }
}
