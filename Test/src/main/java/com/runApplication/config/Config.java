package com.runApplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.runApplication.service.AuthInteceptor;

@Configuration
public class Config implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册TestInterceptor拦截器
		registry.addInterceptor(new AuthInteceptor())
				//需要拦截的请求
				.addPathPatterns("/testView/index.html", "/controller/initPage", "/controller/goOut")
				//不需要拦截的请求
				.excludePathPatterns("/login/index.html");

	}

}
