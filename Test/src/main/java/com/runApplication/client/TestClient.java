package com.runApplication.client;

import java.util.List;
import java.util.Map;

public interface TestClient {
	
	//登陆
	public List<Map<String, Object>> login(Map<String, Object>param);

	//查询
	public List<Map<String, Object>> getListData(Map<String, Object> params);
	
	//更新查询
	public List<Map<String, Object>> updateListData();
	
	//删除
	public int deleteData(Map<String, Object> params);
	
	//新增
	public int addData(Map<String, Object> params);
	
	
	//修改
	public int uodateData(Map<String, Object> params);
	
	//Excel导入
	public int insertExcel(Map<String, Object> params);
}
