package com.runApplication.service;

import com.runApplication.client.KindWomen;

/**
 * 代理模式
 * @author ASUS
 */
public class JiaShi implements KindWomen{

	public void makeEyesWithMan() {
		System.out.println("贾氏抛媚眼");
	}
	
	
	public void happyWithMan() {
		System.out.println("贾氏正在Happy中......");
	}

}
