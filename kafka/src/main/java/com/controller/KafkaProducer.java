package com.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//生产者
public class KafkaProducer {
	public static Logger log = LogManager.getLogger(KafkaProducer.class);
	
	 @Autowired
	    private KafkaTemplate<String, Object> kafkaTemplate;
	 
	 
	    // 发送消息
	    @RequestMapping("/kafka/normal/{message}")
	    public void sendMessage1(@PathVariable("message") String normalMessage) {
	        kafkaTemplate.send("topic1", normalMessage);
	    }

}