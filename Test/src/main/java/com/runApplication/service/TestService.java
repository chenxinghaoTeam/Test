package com.runApplication.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.runApplication.client.TestClient;
//启动缓存并指定缓存名称为Persons
@CacheConfig(cacheNames = "Persons")
@Service
public class TestService implements TestClient {
	public static final Logger Logger = LogManager.getLogger(TestService.class);
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 查询
	 */
	//@Cacheable(如果有数据直接读redis缓存并指定key)
	@Cacheable(key ="'cxh'") 
	public List<Map<String, Object>> getListData(Map<String, Object> params) {
		System.out.println("执行数据库操作");
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Persons t ");
		// sql.append("join Login t1 ");
		// sql.append("on t.id_p = t1.id ");
		sql.append("where 1 = 1 ");
		if (params.containsKey("name") && !"".equals(String.valueOf(params.get("name")))) {
			sql.append("and t.lastname = :name");
			param.put("name", params.get("name"));
		}
		//下拉初始姓名
		if (params.containsKey("firstName") && !"".equals(String.valueOf(params.get("firstName")))) {
			sql.append("and t.lastname like '%"+params.get("firstName")+"%'");
		}
		//下拉地区
		if (params.containsKey("address") && !"".equals(String.valueOf(params.get("address")))) {
			sql.append("and t.address like '%"+params.get("address")+"%'");
		}
		
		//导出ID(批量)
		if (params.containsKey("queryId") && !"".equals(String.valueOf(params.get("queryId")))) {
			sql.append("and t.id_p in (");
			String[] zjId = String.valueOf(params.get("queryId")).split(",");
			for (int i = 0; i < zjId.length; i++) {
				if (i == 0) {
					sql.append("'" + zjId[i] + "'");
				} else {
					sql.append("," + "'" + zjId[i] + "'");
				}
			}
			sql.append(")");
		}
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("查询失败!");
		}

		return result;
	}

	
	//更新后查询(更新指定的缓存key)
	@CachePut(key ="'cxh'") 
	public List<Map<String, Object>> updateListData() {
		System.out.println("更新后查询数据库并存入redis中！！！");
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Persons t ");
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("查询失败!");
		}
		return result;
	
	}
	/**
	 * 删除操作
	 */
	public int deleteData(Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("delete from Persons t where 1 = 1 ");
		if (!"".equals(String.valueOf(params.get("ids")))) {
			sql.append("and t.id_p in (");
			String[] zjId = String.valueOf(params.get("ids")).split(",");
			for (int i = 0; i < zjId.length; i++) {
				if (i == 0) {
					sql.append("'" + zjId[i] + "'");
				} else {
					sql.append("," + "'" + zjId[i] + "'");
				}
			}
			sql.append(")");
		}
		try {
			result = jdbcTemplate.update(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("删除失败!");
		}

		return result;
	}

	/**
	 * 新增
	 */
	public int addData(Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("insert into Persons (id_p,lastName,firstName,address,city) ");
		sql.append("values(:id_ps,:lastNames,:firstNames,:addresss,:citys)");
		String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。。
		param.put("id_ps", s);
		if (!"".equals(String.valueOf(params.get("lastName")))) {
			param.put("lastNames", params.get("lastName"));
		}
		if (!"".equals(String.valueOf(params.get("address")))) {
			param.put("addresss", params.get("address"));
		}
		if (!"".equals(String.valueOf(params.get("firstName")))) {
			param.put("firstNames", params.get("firstName"));
		}
		if (!"".equals(String.valueOf(params.get("city")))) {
			param.put("citys", params.get("city"));
		}

		try {
			result = jdbcTemplate.update(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("新增失败!");
		}

		return result;
	}

	/**
	 * 修改操作
	 * 
	 * @param params
	 * @return
	 */
	public int uodateData(Map<String, Object> params) {
		Map<String, Object> param = new HashMap<>();
		int result = 0;
		StringBuffer sql = new StringBuffer();
		String fql = "";
		sql.append("update Persons t set ");
		if (!"".equals(String.valueOf(params.get("lastName")))) {
			if ("".equals(fql)) {
				fql+="t.lastName = :lastNames";
			} else {
				fql+=",t.lastName = :lastNames";
			}
			param.put("lastNames", params.get("lastName"));
		}
		if (!"".equals(String.valueOf(params.get("address")))) {
			if ("".equals(fql)) {
				fql+="t.address = :addresss";
			} else {
				fql+=",t.address = :addresss";
			}
			param.put("addresss", params.get("address"));
		}
		if (!"".equals(String.valueOf(params.get("firstName")))) {
			if ("".equals(fql)) {
				fql+="t.firstName = :firstNames";
			} else {
				fql+=",t.firstName = :firstNames";
			}
			
			param.put("firstNames", params.get("firstName"));
		}
		if (!"".equals(String.valueOf(params.get("city")))) {
			if ("".equals(fql)) {
				fql+="t.city = :citys";
			} else {
				fql+=",t.city = :citys";
			}
			param.put("citys", params.get("city"));
		}
		sql.append(fql);
		sql.append(" where t.id_p = :id ");
		param.put("id", params.get("id"));
		try {
			result = jdbcTemplate.update(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("更新失败!");
		}

		return result;
	}

	//登陆
	public List<Map<String, Object>> login(Map<String, Object> params) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from Login t ");
		sql.append("where 1 = 1 ");
		if (!"".equals(String.valueOf(params.get("username")))) {
			sql.append("and t.name = :usernames ");
			param.put("usernames", params.get("username"));
		}
		if (!"".equals(String.valueOf(params.get("password")))) {
			sql.append("and t.passWord = :passwords");
			param.put("passwords", params.get("password"));
		}
		try {
			result = jdbcTemplate.queryForList(sql.toString(), param);
		} catch (Exception e) {
			Logger.error("查询失败!");
		}
		return result;
	}

}
