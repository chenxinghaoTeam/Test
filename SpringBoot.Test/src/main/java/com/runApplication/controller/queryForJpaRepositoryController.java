package com.runApplication.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.runApplication.client.queryForJpaRepositoryClient;
import com.runApplication.entity.Persons;
import com.runApplication.viewMode.PageResultForBootstrap;

import net.minidev.json.JSONObject;



@RestController
@RequestMapping("/jpaRepository")
public class queryForJpaRepositoryController {
	public static Logger log = LogManager.getLogger(queryForJpaRepositoryController.class);
	@Autowired
	queryForJpaRepositoryClient queryForJpaRepositoryClient;
	
	
	/**
	 *初始化查询、带参查询
	 * @param name
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/getResult")
	public PageResultForBootstrap PageResultForBootstrap(String jsonParm) {
			String name = "";
			String address = "";
			//利用阿里巴巴封装的FastJSON来转换json字符串
			Map map = (Map)JSON.parse(jsonParm);
			if(map != null) {
				Map<String,Object> jsonParam = (Map<String, Object>) map.get("param");
				if(String.valueOf(jsonParam.get("name"))!= null) {
					name = String.valueOf(jsonParam.get("name"));
				}
				if(String.valueOf(jsonParam.get("address"))!= null) {
					address = String.valueOf(jsonParam.get("address"));
				}
			}
			//在service通过条件查询获取指定页的数据的list
	        List<Persons> result = queryForJpaRepositoryClient.getPersonsResult(name,address);
	        //根据查询条件m，获取符合查询条件的数据总量
	        int total = result.size();
	        //自己封装的数据返回类型，bootstrap-table要求服务器返回的json数据必须包含：totlal，rows两个节点
	        PageResultForBootstrap page = new PageResultForBootstrap();
	        page.setTotal(total);
	        page.setRows(result);
	        return page;
	    }
	
	
	/**
	 * 新增操作
	 * @param name
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/add")
	public PageResultForBootstrap addData(String jsonParm) {
			PageResultForBootstrap page = new PageResultForBootstrap();
			String name = "";
			String address = "";
			String firstName = "";
			String city = "";
			SimpleDateFormat sm = new SimpleDateFormat("yyyyMMddHHmmsSSSS");
			long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子作为主键ID
			Map map = (Map)JSON.parse(jsonParm);
			if(map != null) {
				@SuppressWarnings("unchecked")
				Map<String,Object> jsonParam = (Map<String, Object>) map.get("param");
				if(String.valueOf(jsonParam.get("name"))!= null) {
					name = String.valueOf(jsonParam.get("name"));
				}
				if(String.valueOf(jsonParam.get("address"))!= null) {
					address = String.valueOf(jsonParam.get("address"));
				}
				if(String.valueOf(jsonParam.get("firstName"))!= null) {
					firstName = String.valueOf(jsonParam.get("firstName"));
				}
				if(String.valueOf(jsonParam.get("city"))!= null) {
					city = String.valueOf(jsonParam.get("city"));
				}
			}
	        int number = queryForJpaRepositoryClient.addPersons(seed,name, address, firstName, city);
	        page.setAddSussful(number);
	        return page;
	    }
	
	/**
	 * 删除操作
	 * @param name
	 * @param address
	 * @return
	 * @RequestParam(value = "id", defaultValue = "") long id
	 */
	@RequestMapping(value = "/delete")
	public PageResultForBootstrap deleteData(String jsonParm) {
			PageResultForBootstrap page = new PageResultForBootstrap();
			Map map = (Map)JSON.parse(jsonParm);
			String id = "";
			if(map != null) {
				@SuppressWarnings("unchecked")
				Map<String,Object> jsonParam = (Map<String, Object>) map.get("param");
				if(String.valueOf(jsonParam.get("id"))!= null) {
					id = String.valueOf(jsonParam.get("id"));
				}
			}
			
			List<Long> ids = new ArrayList<Long>();
			if(id.contains(",")) {
				 String[] stuList = id.split(",");
				for(String str : stuList){
					ids.add(new Long(str));
			    }
			}else {
				ids.add(new Long(id));
			}
			
			int deleteNumber = queryForJpaRepositoryClient.deleteData(ids);
			page.setDeleteSussful(deleteNumber);
	        return page;
	    }
	
	/**
	 * 更新操作
	 * @param name
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/update")
	public PageResultForBootstrap updateData(@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "firstName", defaultValue = "") String firstName,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "id", defaultValue = "") long id) {
			PageResultForBootstrap page = new PageResultForBootstrap();
			int updateCount = queryForJpaRepositoryClient.updateData(lastName, address, firstName, city, id);
			page.setUpdateSussful(updateCount);
	        return page;
	    }
}