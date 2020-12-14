package com.runApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.runApplication.client.testViewClient;

//@Component  //报Consider defining a bean of type错误，无法找到bean
@Repository(value = "testViewService")
public class testViewService implements testViewClient{
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	
	
	public List<Map<String, Object>> getTestViewData() {
		List<Map<String, Object>> result= new ArrayList<>(	);
		 String sql = "select * from Persons ";
//		 result = jdbcTemplate.queryForList(sql);
	     System.out.println("执行完成");
		return result;
	}
	
}
