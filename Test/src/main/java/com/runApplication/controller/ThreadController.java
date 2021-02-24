package com.runApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runApplication.service.ThreadingService;

@RestController
@RequestMapping("/threadController")
public class ThreadController {

	@Autowired
	private ThreadingService service;

	@RequestMapping("/dotask")
	public String doTask() {
		 
        try {
            for (int i=0;i<100;i++){
            	service.executeAysncTask(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 
        return "success";
    }


}
