package com.java.nie.controller;


import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RabbitTemplate rabbitTemplate ;


    @GetMapping("/dddemo")
    public void  demo(){
        String  msg = "----------------->"+new Date();
        rabbitTemplate.convertAndSend("nch_direct","nch",msg);

    }


}
