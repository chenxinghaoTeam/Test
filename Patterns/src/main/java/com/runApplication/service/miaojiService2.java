package com.runApplication.service;

import com.runApplication.client.miaojiClient;

/**
 * 策略模式
 * @author ASUS
 */
public class miaojiService2 implements miaojiClient{

	@Override
	public void operate() {
		System.out.println("孙夫人断后，挡住追兵");
	}

}
