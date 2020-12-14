package com.runApplication.controller;

import java.util.List;

//创建实现类
public class Thread1 implements Runnable{

	int i = 0;
	@Override//实现run方法
	public void run() {
		for (; i < 100; i++) {
			String name = Thread.currentThread().getName();
			System.out.println(name+":"+i);
		}
		
	}
	public static void main(String[] args) {
		Thread1 th = new Thread1();//实例化
		Thread t = new Thread(th);//线程1
		Thread t1 = new Thread(th);//线程2
		t.start();
		t1.start();
		/**
		 * 
		 */
	}
}