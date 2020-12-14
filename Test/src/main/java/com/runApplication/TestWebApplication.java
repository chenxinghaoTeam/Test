package com.runApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//SpringBoot 启动时会自动注入数据源和配置 jpa 陈行昊
public class TestWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestWebApplication.class, args);
	}
}
