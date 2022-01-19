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

    /**
     * 延时队列监听
     *
     * @param message
     * @param channel
     * @param params
     */
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME})
    public void consumer(Message message, Channel channel, Object params) {
        logger.info("params:{}", params);
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由模式 监听
     *
     * @param message
     * @param channel
     * @param params
     */
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_DIRECT_DEMO_ONE})
    public void directConsumerOne(Message message, Channel channel, Object params) {
        logger.info("params:{}", params);
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_DIRECT_DEMO_TWO})
    public void directConsumerTwo(Message message, Channel channel, Object params) {
        logger.info("params:{}", params);
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 主题模式 监听
     *
     * @param message
     * @param channel
     * @param params
     */
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_TOPIC_DEMO_ONE})
    @RabbitHandler
    public void topicConsumerOne(Message message, Channel channel, Object params) {
        logger.info("params:{}", "warning");
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_TOPIC_DEMO_TWO})
    @RabbitHandler
    public void topicConsumerTwo(Message message, Channel channel, Object params) {
        logger.info("params:{}", "info");
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_TOPIC_DEMO_THREE})
    @RabbitHandler
    public void topicConsumerThree(Message message, Channel channel, Object params) {
        logger.info("params:{}", "error");
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = {RabbitMQConfig.QUEUE_HEADERS_DEMO_ONE})
    public void headerConsumerOne(Message message, Channel channel, Object params) {
        logger.info("params1:{}", params);
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RabbitListener(queues = {RabbitMQConfig.QUEUE_HEADERS_DEMO_TWO})
    public void headerConsumerTwo(Message message, Channel channel, Object params) {
        logger.info("params2:{}", params);
        try {
            //第二个参数--- true:批量接收数据，false:逐条接收数据
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
