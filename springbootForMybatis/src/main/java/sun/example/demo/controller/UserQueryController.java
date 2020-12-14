package sun.example.demo.controller;
  
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import sun.example.demo.dao.UserMapper;
  
  
@RestController
@RequestMapping("/controller")
public class UserQueryController {

/**
 * @Autowired与@Resource都可以用来装配bean. 都可以写在字段上
	@Resource（这个注解属于J2EE的），默认按照名称进行装配，名称可以通过name属性进行指定，
	如果没有指定name属性，当注解写在字段上时，默认取字段名进行安装名称查找	
 */
 @Autowired
 UserMapper service;
   
 @RequestMapping("/listu")
 public List<Map<String, Object>> listUser(String jsonParm) {
	 Map<String, Object> map= (Map)JSON.parse(jsonParm);
	 List<Map<String, Object>> result = service.cxh(map);
	 return result;
 }
 
 
 /**
  * 登陆
  * @param jsonParm
  * @return
  */
 @RequestMapping("/login")
 public int login(String jsonParm) {
	 int flag = 0;
	 Map<String, Object> map= (Map)JSON.parse(jsonParm);
	 List<Map<String, Object>> result = service.login(map);
	 if(result.size() == 1) {
		 flag = 1;
	 }else {
		 flag = 0;
	 }
	 return flag;
 }
 
 
}
