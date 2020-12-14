package com.runApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//SpringBoot 启动时会自动注入数据源和配置 jpa
public class webApplication {
	public static void main(String[] args) {
		SpringApplication.run(webApplication.class, args);
	}
}
