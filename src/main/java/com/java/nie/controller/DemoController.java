package com.java.nie.controller;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.util.DateUtils;
import com.java.nie.config.MonthTableNameHandler;
import com.java.nie.config.RabbitMQConfig;
import com.java.nie.config.RedisService;
import com.java.nie.domain.YarntaskLog;
import com.java.nie.mapper.YarntaskLogMapper;
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

    @Resource
    private YarntaskLogMapper yarntaskLogMapper ;

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

    @GetMapping("/dddemo")
    public void demo(String id) {
        //指定延时队列交换机   延时队列路由  和  设置消息过期时间
//        rabbitTemplate.convertAndSend(RabbitMQConfig.DIRECT_DELAY_EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_DELAY, id, mes -> {
//            mes.getMessageProperties().setExpiration(1000 * 20 + "");
//            return mes;
//        });

        /**
         * redis 测试
         */
        long expire = redisService.getExpire("DS2022:mail");
        System.err.println("剩余时间为-----------------》" + expire);

    }

    @GetMapping("/test")
    public void test() {
        Object hget = redisService.hget("mail", "kuwoCount");
        Object hget1 = redisService.hget("demo", "kuwoCount");
        System.err.println("hget-----------------》" + hget);
        System.err.println("===============================================");
        System.err.println("hget1-----------------》" + hget1);
    }

    @GetMapping("/set")
    public void set(Boolean isSend) {
        redisService.hset("DS2022:cache:config", "isSend", isSend);
    }


    public static void main(String[] args) {
        Date now = DateUtil.beginOfDay(new Date());
        Date offDay = DateUtil.offsetDay(now, -6);
        List<DateTime> dateTimes = DateUtil.rangeToList(offDay, now, DateField.DAY_OF_MONTH);
        for (DateTime x : dateTimes) {
            String format = new SimpleDateFormat(DateUtils.DATE_FORMAT_10).format(x);
            System.out.println(format);
        }
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


    @GetMapping("/yarnLog")
    public void yarnLog(){
        MonthTableNameHandler.setMonth("202211");
        int insert = yarntaskLogMapper.insert(new YarntaskLog());
        System.out.println(insert);
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
