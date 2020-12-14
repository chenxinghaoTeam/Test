package sun.example.demo.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestMapping;


@Mapper
public interface UserMapper {


	@RequestMapping("/cxh")
	public List<Map<String, Object>> cxh(Map<String, Object>param);
 
 
	 /**
	  * 登陆
	  * @param param
	  * @return
	  */
	 
	 public List<Map<String, Object>> login (Map<String, Object>param);
}
