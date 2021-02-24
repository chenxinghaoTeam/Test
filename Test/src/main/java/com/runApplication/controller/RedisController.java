package com.runApplication.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis使用
 * https://blog.csdn.net/sinat_22797429/article/details/89196933   redis方法说明
 * @author ASUS
 *
 */
@RestController
@RequestMapping("/RedisController")
public class RedisController {
	public static final Logger log = LoggerFactory.getLogger(RedisController.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@RequestMapping("/test")
	public void test() {
//		redisTemplate.opsForValue().set("user:1", "测试");
//		redisTemplate.opsForValue().set("user:2", "测试2");
//		redisTemplate.opsForValue().set("user:3", "测试3");
//		redisTemplate.opsForValue().set("user:4", "测试4");
//		redisTemplate.opsForValue().set("5", "5");
//		redisTemplate.opsForValue().set("6", "6");
		
		
		//判断是否有key所对应的值，有则返回true，没有则返回false
		if(redisTemplate.hasKey("6")) {
			System.out.println("存在");
			//有则取出key值所对应的值
			Object result = redisTemplate.opsForValue().get("6");
			System.out.println("result:"+result);
		}
		//删除单个key值 redisTemplate.delete(key);
		
		//批量删除key redisTemplate.delete(keys)
		
		//设置过期时间  redisTemplate.expire(key, timeout, unit);
		
		//查找匹配的key值，返回一个Set集合类型
		Set<String> result = redisTemplate.keys("6");
		System.out.println(result);
		
		//修改redis中key的名称 redisTemplate.rename(oldKey, newKey);
		
		
		//如果旧值存在时，将旧值改为新值 redisTemplate.renameIfAbsent(oldKey, newKey);
		
		//设置map集合到redis
		Map<String, Object> valueMap = new HashMap<>(); 
		valueMap.put("valueMap1","map1");  
		valueMap.put("valueMap2","map2");  
		valueMap.put("valueMap3","map3");  
		redisTemplate.opsForValue().set("map", valueMap);
		
		//如果对应的map集合名称不存在，则添加否则不做修改
		Map<String, Object> map = new HashMap<>(); 
		map.put("1","1");  
		map.put("2","2");  
		map.put("3","3");  
		redisTemplate.opsForValue().multiSetIfAbsent(map); 

	}
}
