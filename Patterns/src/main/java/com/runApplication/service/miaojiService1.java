package com.runApplication.service;

import com.runApplication.client.miaojiClient;


/**
 * 策略模式
 * @author ASUS
 */
public class miaojiService1 implements miaojiClient{

	@Override
	public void operate() {
		System.out.println("找乔国老帮忙，让吴国太给孙权施加压力");
		
	}

}
