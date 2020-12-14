package com.runApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//SpringBoot 启动时会自动注入数据源和配置 jpa
//@EnableAutoConfiguration //自动加载配置信息
public class webApplication {
	public static void main(String[] args) {
		SpringApplication.run(webApplication.class, args);
	}
}
