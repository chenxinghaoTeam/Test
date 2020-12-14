package com.runApplication.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.runApplication.client.testViewClient;
import com.runApplication.service.testViewService;


@RestController
@RequestMapping("/test")
public class testViewController {
	public static Logger log = LogManager.getLogger(testViewController.class);
	@Autowired
	testViewService testView;
	
	public List<String[]> getService(){
		List<Map<String, Object>> result =testView.getTestViewData();
//		[{ID_P=1, LASTNAME=陈行昊, FIRSTNAME=陈昊, ADDRESS=高新区, CITY=南京}]
		List<String[]> result2 = new ArrayList<>();
		for (int i = 0; i < result.size(); i++) {
			String a = "";
			a = result.get(i).get("LASTNAME")+","+result.get(i).get("FIRSTNAME")+","+result.get(i).get("ADDRESS")+","+result.get(i).get("CITY");
			String [] b =a.split(",");
			result2.add(b);
		}
		return result2;
	}
	
	@RequestMapping("/exportExcel")
	    public  void exportExcel(HttpServletResponse response) {
			//标题
	        String[] str = {"姓名","初始姓名","地址","城市"};
	        List<String[]> data = getService();
	        try {
	            //实例化HSSFWorkbook
	            HSSFWorkbook workbook = new HSSFWorkbook();
	            //创建一个Excel表单，参数为sheet的名字
	            HSSFSheet sheet = workbook.createSheet("sheet");
	            //设置表头
	            setTitle(workbook, sheet, str);
	            //设置单元格并赋值
	            setData(sheet, data);
	            //设置浏览器下载
	            setBrowser(response, workbook, "aa");
	            log.info("导出解析成功!");
	        } catch (Exception e) {
	            log.info("导出解析失败!");
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * 方法名：setTitle
	     * 功能：设置表头
	     * 描述：
	     * 创建人：typ
	     * 创建时间：2018/10/19 10:20
	     * 修改人：
	     * 修改描述：
	     * 修改时间：
	     */
	    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] str) {
	        try {
	            HSSFRow row = sheet.createRow(0);
	            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
	            for (int i = 0; i <= str.length; i++) {
	                sheet.setColumnWidth(i, 15 * 256);
	            }
	            //设置为居中加粗,格式化时间格式
	            HSSFCellStyle style = workbook.createCellStyle();
	            HSSFFont font = workbook.createFont();
	            font.setBold(true);
	            style.setFont(font);
	            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
	            //创建表头名称
	            HSSFCell cell;
	            for (int j = 0; j < str.length; j++) {
	                cell = row.createCell(j);
	                cell.setCellValue(str[j]);
	                cell.setCellStyle(style);
	            }
	        } catch (Exception e) {
	            log.info("导出时设置表头失败！");
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * 方法名：setData
	     * 功能：表格赋值
	     * 描述：
	     * 创建人：typ
	     * 创建时间：2018/10/19 16:11
	     * 修改人：
	     * 修改描述：
	     * 修改时间：
	     */
	    private static void setData(HSSFSheet sheet, List<String[]> data) {
	        try{
	            int rowNum = 1;
	            for (int i = 0; i < data.size(); i++) {
	                HSSFRow row = sheet.createRow(rowNum);
	                for (int j = 0; j < data.get(i).length; j++) {
	                    row.createCell(j).setCellValue(data.get(i)[j]);
	                }
	                rowNum++;
	            }
	            log.info("表格赋值成功！");
	        }catch (Exception e){
	            log.info("表格赋值失败！");
	            e.printStackTrace();
	        }
	    }
	 
	    /**
	     * 方法名：setBrowser
	     * 功能：使用浏览器下载
	     * 描述：
	     * 创建人：typ
	     * 创建时间：2018/10/19 16:20
	     * 修改人：
	     * 修改描述：
	     * 修改时间：
	     */
	    private static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
	        try {
	            //清空response
	            response.reset();
	            //设置response的Header
	            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
	            OutputStream os = new BufferedOutputStream(response.getOutputStream());
	            response.setContentType("application/vnd.ms-excel;charset=gb2312");
	            //将excel写入到输出流中
	            workbook.write(os);
	            os.flush();
	            os.close();
	            log.info("设置浏览器下载成功！");
	        } catch (Exception e) {
	            log.info("设置浏览器下载失败！");
	            e.printStackTrace();
	        }
	 
	    }
	 
	 
	
}
