package com.java.nie;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.java.nie.mapper")
@EnableCaching
public class NieApplication {

    private final static Logger logger = LoggerFactory.getLogger(NieApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(NieApplication.class, args);
            logger.info("启动成功！！！！！！！！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
