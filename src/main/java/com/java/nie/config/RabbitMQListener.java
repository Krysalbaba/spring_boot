package com.java.nie.config;


import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitMQListener {


    @RabbitListener(bindings = {
            @QueueBinding( value = @Queue(value = "nch_direct",durable = "true"),
                    exchange =@Exchange(value = "nch_direct",type = "direct"),
                    key = "nch")
    })
    public void direct(String msg){
        System.out.println(new Date());
        System.err.println(msg);
    }
}
