package com.runApplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2021年6月11日 下午2:11:36 类说明
 */

@Configuration
public class WebSocketConfig {
	 @Bean
	 public ServerEndpointExporter serverEndpointExporter() {
	 return new ServerEndpointExporter();
	 }
}
