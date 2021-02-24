package com.runApplication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadingService {
	private static final Logger logger = LoggerFactory.getLogger(ThreadingService.class);

	/*
	 * 如何让异步调用的执行任务使用这个线程池中的资源来运行呢？方法非常简单，我们只需要在@Async注解中指定线程池名即可
	 */

	@Async("nbpiTaskExecutor")
	public void executeAysncTask(Integer i) throws InterruptedException {
		logger.info("CustomMultiThreadingService ==> executeAysncTask1 method: 执行异步任务{} ", i);
		Thread.sleep(500L);
	}

}
