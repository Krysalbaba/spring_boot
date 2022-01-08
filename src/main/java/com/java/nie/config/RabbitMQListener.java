package com.java.nie.config;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RabbitMQListener {

    private final static Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
    public void consumer(Message message, Channel channel,Object params){
        logger.info("params:{}",params);
        try{
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_DIRECT_DEMO_ONE})
    public void directConsumerOne(Message message, Channel channel,Object params){
        logger.info("params:{}",params);
        try{
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_DIRECT_DEMO_TWO})
    public void directConsumerTwo(Message message, Channel channel,Object params){
        logger.info("params:{}",params);
        try{
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
