package sun.example.demo.service;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import sun.example.demo.dao.UserMapper;


@Component
@WebService(targetNamespace = "http://service.demo.example.sun", serviceName = "services")
public class UserMapperService {
	
	@Autowired
	 UserMapper service;
	   
	 @RequestMapping("/listu")
	 public List<Map<String, Object>> listUser(String jsonParm) {
		 Map<String, Object> map= (Map)JSON.parse(jsonParm);
		 List<Map<String, Object>> result = service.cxh(map);
		 return result;
	 }
}
