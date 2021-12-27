package com.java.nie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.java.nie.mapper")
@EnableCaching
public class NieApplication  implements CommandLineRunner {

	public static void main(String[] args) {
		try {
			SpringApplication.run(NieApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("启动成功！！！！！！！！！！");
	}
}
