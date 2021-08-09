package com.runApplication.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.runApplication.client.TestClient;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@RestController
@RequestMapping("/ExcelController")
public class ExcelController {

	@Autowired
	private TestClient service;

	/**
	 * pol导出（Apache POI）
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downloadAllClassmate")
	public void downloadAllClassmate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 创建工作表
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作簿
		HSSFSheet sheet = workbook.createSheet("信息表");
		// 设置默认宽度
		sheet.setDefaultColumnWidth(20);
		/*********************** 以下为样式 ************************/
		// 创建样式
		CellStyle style = workbook.createCellStyle();
		// 水平对齐方式（居中）
		style.setAlignment(HorizontalAlignment.CENTER);
		Font baseFont = workbook.createFont();
		// 字体
		baseFont.setFontName("宋体");
		baseFont.setColor(IndexedColors.RED.getIndex());
		baseFont.setBold(true);
		// 大小
		baseFont.setFontHeightInPoints((short) 12);
		style.setFont(baseFont);
		// 设置边框
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		/*********************** 以上为样式 ************************/

		Map<String, Object> map1 = new HashMap<>();
		map1.put("firstName", request.getParameter("name") == null ? "" : request.getParameter("name"));
		map1.put("address", request.getParameter("address") == null ? "" : request.getParameter("address"));
		map1.put("queryId", request.getParameter("queryId"));
		List<Map<String, Object>> result = service.getListData(map1);

		String fileName = "人员信息.xls";// 设置要导出的文件的名字
		String[] headers = { "ID", "初始姓名", "小名", "地区", "城市" };
		// headers表示excel表中第一行的表头
		HSSFRow row = sheet.createRow(0);
		// 在excel表中添加表头
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			cell.setCellStyle(style);
		}

		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// 在表中存放查询到的数据放入对应的列
		for (Map<String, Object> a : result) {
			// 表中每行
			HSSFRow row1 = sheet.createRow(rowNum);
			// 每列
			row1.createCell(0).setCellValue(String.valueOf(a.get("ID_P")));
			row1.createCell(1).setCellValue(String.valueOf(a.get("LASTNAME")));
			row1.createCell(2).setCellValue(String.valueOf(a.get("FIRSTNAME")));
			row1.createCell(3).setCellValue(String.valueOf(a.get("ADDRESS")));
			row1.createCell(4).setCellValue(String.valueOf(a.get("CITY")));
			rowNum++;
		}

		response.setContentType("application/octet-stream");// 客户端的响应的内容类型(application/octet-stream不知道下载文件类型)
		/**
		 * Content-Disposition为属性名 disposition-type是以什么方式下载，如attachment为以附件方式下载
		 * disposition-parm为默认保存时的文件名
		 */
		response.setHeader("Content-disposition",
				"attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	/**
	 * jxl导出jExcelAPI(即jxl)。
	 *
	 * @return
	 */
	@RequestMapping("/download")
	public String download(String jsonParm) {
		Map map = (Map) JSON.parse(jsonParm);
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletResponse response = requestAttributes.getResponse();
		HttpServletRequest request = requestAttributes.getRequest();

		// 文件名
		String filename = "地址列表.xls";

		try {

			// 写到服务器上
			String path = request.getSession().getServletContext().getRealPath("") + "/" + filename;

			File name = new File(path);
			// 创建写工作簿对象
			WritableWorkbook workbook = Workbook.createWorkbook(name);
			// 工作表
			WritableSheet sheet = workbook.createSheet("地址列表", 0);
			// 设置字体;
			WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(font);
			// 设置背景颜色;
			cellFormat.setBackground(Colour.WHITE);
			// 设置边框;
			cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
			// 设置文字居中对齐方式;
			cellFormat.setAlignment(Alignment.CENTRE);
			// 设置垂直居中;
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 分别给1,5,6列设置不同的宽度;
			sheet.setColumnView(0, 15);
			sheet.setColumnView(4, 60);
			sheet.setColumnView(5, 35);
			// 给sheet电子版中所有的列设置默认的列的宽度;
			sheet.getSettings().setDefaultColumnWidth(20);
			// 给sheet电子版中所有的行设置默认的高度，高度的单位是1/20个像素点,但设置这个貌似就不能自动换行了
			// sheet.getSettings().setDefaultRowHeight(30 * 20);
			// 设置自动换行;
			cellFormat.setWrap(true);

			// 单元格
			// {CITY=南京, LASTNAME=泽希, ADDRESS=大厂, FIRSTNAME=陈泽希, ID_P=123}
			Label label0 = new Label(0, 0, "ID", cellFormat);
			Label label1 = new Label(1, 0, "初始姓名", cellFormat);
			Label label2 = new Label(2, 0, "小名", cellFormat);
			Label label3 = new Label(3, 0, "地区", cellFormat);
			Label label4 = new Label(4, 0, "城市", cellFormat);

			sheet.addCell(label0);
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);

			// 给第二行设置背景、字体颜色、对齐方式等等;
			WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
			// 设置文字居中对齐方式;
			cellFormat2.setAlignment(Alignment.CENTRE);
			// 设置垂直居中;
			cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat2.setBackground(Colour.WHITE);
			cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
			cellFormat2.setWrap(true);

			// 记录行数
			int n = 1;
			// {CITY=南京, LASTNAME=泽希, ADDRESS=大厂, FIRSTNAME=陈泽希, ID_P=123}
			//
			Map<String, Object> map1 = new HashMap<>();
			map1.put("name", "");
			map1.put("firstName", "");
			map1.put("address", "");
			List<Map<String, Object>> addressList = service.getListData(map1);
			if (addressList != null && addressList.size() > 0) {
				// 遍历
				for (Map<String, Object> a : addressList) {

					Label lt0 = new Label(0, n, String.valueOf(a.get("ID_P")), cellFormat2);
					Label lt1 = new Label(1, n, String.valueOf(a.get("LASTNAME")), cellFormat2);
					Label lt2 = new Label(2, n, String.valueOf(a.get("FIRSTNAME")), cellFormat2);
					Label lt3 = new Label(3, n, String.valueOf(a.get("ADDRESS")), cellFormat2);
					Label lt4 = new Label(4, n, String.valueOf(a.get("CITY")), cellFormat2);
					sheet.addCell(lt0);
					sheet.addCell(lt1);
					sheet.addCell(lt2);
					sheet.addCell(lt3);
					sheet.addCell(lt4);
					n++;
				}
			}

			// 开始执行写入操作
			workbook.write();
			// 关闭流
			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 第六步，下载excel

		OutputStream out = null;
		try {

			// 1.弹出下载框，并处理中文
			response.addHeader("content-disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(filename, "utf-8"));

			// 2.下载
			out = response.getOutputStream();
			String path3 = request.getSession().getServletContext().getRealPath("") + "/" + filename;

			// inputStream：读文件，前提是这个文件必须存在，要不就会报错
			InputStream is = new FileInputStream(path3);

			byte[] b = new byte[4096];
			int size = is.read(b);
			while (size > 0) {
				out.write(b, 0, size);
				size = is.read(b);
			}
			out.close();
			is.close();
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}

	/**
	 * Excel导入操作
	 */
	@RequestMapping("/InputExcel")
	public String inputExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		long startTime = System.currentTimeMillis();
		String fileName = file.getOriginalFilename();// 获取文件名
		InputStream is = file.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(is);
		int okrows = 0;
		// 得到第一个shell
		HSSFSheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		int totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		int totalCells = 0;
		if (totalRows > 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		// 行数
		String text = "";
		String ids = "";
		for (int i = 1; i < totalRows; i++) {
			Map<String, Object> map = new HashMap<>();
			;
			// 第几行
			Row row = sheet.getRow(i);
			// 列数
			for (int j = 0; j < totalCells; j++) {
				// 每行的列
				Cell cell = row.getCell(j);
				if (cell != null) {
					//只能读取字段为String类型的,其他类型的字段暂不支持。方法自写
					String value = String.valueOf(cell.getRichStringCellValue());
					if (j == 0) {
						map.put("id_p", value);
					}
					if (j == 1) {
						map.put("firstName", value);
					}
					if (j == 2) {
						map.put("lastName", value);
					}
					if (j == 3) {
						map.put("address", value);
					}
					if (j == 4) {
						map.put("city", value);
					}
				}
			}
			int isok = service.insertExcel(map);
			if (isok == 0) {
				ids += "," + map.get("id_p");
			} else {
				okrows++;
			}
		}
		long endTime = System.currentTimeMillis();
		float excTime = (float) (endTime - startTime) / 1000;
		if ((totalRows - 1) == okrows) {
			text = "一共" + (totalRows - 1) + "条数据成功导入" + okrows + "条数据,一共消耗:" + excTime + "秒";
		} else {
			text = "一共" + (totalRows - 1) + "条数据成功导入" + okrows + "条数据,一共消耗:" + excTime + "秒,其中存在:"
					+ (totalRows - okrows) + "条数据无法导入！无法导入的id:" + ids;
		}
		return text;
	}
}
