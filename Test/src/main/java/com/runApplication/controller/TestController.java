package com.runApplication.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.runApplication.client.TestClient;
import com.runApplication.viewMode.TestViewMode;

@RestController
@RequestMapping("/controller")
public class TestController {
	public static final Logger log = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private TestClient service;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	
	// 查询列表
	@RequestMapping("/login")
	public TestViewMode login(String jsonParm, HttpServletRequest request) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		List<Map<String, Object>> result = service.login(map);
		Boolean flag = null;
		if (result.size() == 1) {
			flag = true;
			// 用户信息存在session中,用于拦截器
			request.getSession().setAttribute("name", result.get(0).get("name"));
		} else {
			flag = false;
		}
		vm.setSuccessful(flag);
		return vm;
	}

	// 查询列表
	@RequestMapping("/initPage")
	public TestViewMode initPage(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		List<Map<String, Object>> result = null;
		//type为2更新缓存
		if("2".equals(map.get("type"))) {
			result = service.updateListData();
		}else {
			result = service.getListData(map);
		}
		int total = result.size();
		vm.setRows(result);
		vm.setTotal(total);
		vm.setSuccessful(true);
		return vm;
	}

	// 新增数据
	@RequestMapping("/add")
	public TestViewMode add(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		int result = service.addData(map);
		if (result == 1) {
			vm.setSuccessful(true);
		} else {
			vm.setSuccessful(false);
		}

		return vm;
	}

	// 删除数据
	@RequestMapping("/delete")
	public TestViewMode delete(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		int result = service.deleteData(map);
		if (result >= 1) {
			vm.setSuccessful(true);
		} else {
			vm.setSuccessful(false);
		}
		return vm;
	}

	// 修改数据
	@RequestMapping("/update")
	public TestViewMode update(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Map map = (Map) JSON.parse(jsonParm);
		int result = service.uodateData(map);
		if (result == 1) {
			vm.setSuccessful(true);
		} else {
			vm.setSuccessful(false);
		}
		return vm;
	}

	// 删除缓存
	@RequestMapping("/deleteCace")
	public TestViewMode deleteCace(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		Boolean falg = redisTemplate.delete("Persons::cxh");
		vm.setSuccessful(falg);
		return vm;
	}
	
	// 更新缓存
	@RequestMapping("/updateCace")
	public TestViewMode updateCace(String jsonParm) {
		TestViewMode vm = new TestViewMode();
		List<Map<String, Object>>result = service.updateListData();
		int total = result.size();
		vm.setRows(result);
		vm.setTotal(total);
		vm.setSuccessful(true);
		return vm;
		
	}
	
	// 退出操作
	@RequestMapping("/goOut")
	public void goOut(HttpServletRequest request) {
		request.getSession().setAttribute("out", "out");
	}
}
