package sun.example.demo.controller;
  
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import jxl.Sheet;
import jxl.Workbook;
import sun.example.demo.dao.UserMapper;
import sun.example.demo.entity.Persons;

  
  
@RestController
@RequestMapping("/controller")
public class ExcelImportOrcaleController {


	 @Autowired
	 UserMapper service;

    /**
     *Excel导入数据库
     * @return
     */
    @RequestMapping("/importOrcale")
    public void importExcel(String jsonParm) {
    	List<Persons> list = new ArrayList<Persons>();
    	Map<String, Object> map= (Map)JSON.parse(jsonParm);
//    	String path = String.valueOf(map.get("path"));
        try {
        	String path = new File("C://fakepath//info.xls").getAbsolutePath();
        	InputStream is = new FileInputStream(path);
            Workbook rwb = Workbook.getWorkbook(is);
            Sheet rs = rwb.getSheet("用户信息");// 或者rwb.getSheet(0)
            int clos = rs.getColumns();// 得到所有的列
            int rows = rs.getRows();// 得到所有的行
            System.out.println(clos + " rows:" + rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    // 第一个是列数，第二个是行数
                	String id = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
                    if (id == null || "".equals(id))id = "0";
                    // 所以这里得j++
                    String city = rs.getCell(j++, i).getContents();
                    String address = rs.getCell(j++, i).getContents();
                    String firstName = rs.getCell(j++, i).getContents();
                    String lastName = rs.getCell(j++, i).getContents();
                    list.add(new Persons(id, city,
                    		address, firstName, lastName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}
