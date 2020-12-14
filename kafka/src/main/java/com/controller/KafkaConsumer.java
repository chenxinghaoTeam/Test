package com.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;



@RestController
//消费者
public class KafkaConsumer {
	public static Logger log = LogManager.getLogger(KafkaConsumer.class);
	
	 // 消费监听
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


}