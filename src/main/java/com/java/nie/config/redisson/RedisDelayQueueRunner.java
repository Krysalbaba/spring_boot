package com.java.nie.config.redisson;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.java.nie.utils.RedisDelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisDelayQueueRunner implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;

/*    @Autowired
    private ApplicationContext context;*/
    @Autowired
    private ThreadPoolTaskExecutor ptask;


/*    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 5, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000),new ThreadFactoryBuilder().setNameFormat("order-delay-%d").build());*/

    @Override
    public void run(String... args) throws Exception {
        ptask.execute(() -> {
            while (true){
                try {
                    RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque("ORDER_TIMEOUT_NOT_EVALUATED");
                    RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                    String poll = blockingDeque.poll();
                    if (poll != null) {
                        log.info("poll");
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    log.error("(Redission延迟队列监测异常中断) {}", e.getMessage());
                }
            }
        });
        log.info("(Redission延迟队列监测启动成功)");
    }
}
