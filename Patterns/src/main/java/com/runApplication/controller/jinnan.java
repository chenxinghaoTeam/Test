package com.runApplication.controller;

import com.runApplication.client.miaojiClient;

/**
 * 策略模式
 * @author ASUS
 */
public class jinnan {
	//构造函数，你要使用那个妙计
		private miaojiClient mj;
		public jinnan(miaojiClient mj){
		this.mj = mj;
		}
		//使用计谋了，看我出招了
		public void operate(){
		this.mj.operate();
		}
}
