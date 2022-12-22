package com.java.nie.config.redisson;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class OrderPaymentTimeout implements RedisDelayQueueHandle<Map>{


    @Override
    public void execute(Map map) {
        log.info("(收到订单支付超时延迟消息) {}", map);
        // TODO 订单支付超时，自动取消订单处理业务...
    }
}
