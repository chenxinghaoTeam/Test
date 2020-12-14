package com.runApplication.service;

import com.runApplication.client.miaojiClient;


/**
 * 策略模式
 * @author ASUS
 */
public class miaojiService3 implements miaojiClient{

	@Override
	public void operate() {
		System.out.println("求吴国太开个绿灯,放行！");
	}

}
