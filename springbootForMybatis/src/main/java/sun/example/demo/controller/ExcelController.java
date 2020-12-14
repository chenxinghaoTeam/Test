package sun.example.demo.controller;
  

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
import sun.example.demo.dao.UserMapper;

  
  
@RestController
@RequestMapping("/controller")
public class ExcelController {


	 @Autowired
	 UserMapper service;

    /**
     * 下载文件
     *
     * @return
     */
    @RequestMapping({"/download"})
    public void download() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
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
            WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);

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
          //{CITY=南京, LASTNAME=泽希, ADDRESS=大厂, FIRSTNAME=陈泽希, ID_P=123}
            Label label0 = new Label(0, 0, "ID", cellFormat);
            Label label1 = new Label(1, 0, "城市", cellFormat);
            Label label2 = new Label(2, 0, "地区", cellFormat);
            Label label3 = new Label(3, 0, "姓名", cellFormat);
            Label label4 = new Label(4, 0, "乳名", cellFormat);

            sheet.addCell(label0);
            sheet.addCell(label1);
            sheet.addCell(label2);
            sheet.addCell(label3);
            sheet.addCell(label4);

            // 给第二行设置背景、字体颜色、对齐方式等等;
            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
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

            // 查找所有地址
            Map<String, Object> map = new HashMap<String, Object>();
            //{CITY=南京, LASTNAME=泽希, ADDRESS=大厂, FIRSTNAME=陈泽希, ID_P=123}
            List<Map<String, Object>> addressList = service.cxh(map);
            if (addressList != null && addressList.size() > 0) {
                // 遍历
                for (Map<String, Object> a : addressList) {

                    Label lt0 = new Label(0, n, String.valueOf(a.get("ID_P")) , cellFormat2);
                    Label lt1 = new Label(1, n, String.valueOf(a.get("CITY")), cellFormat2);
                    Label lt2 = new Label(2, n, String.valueOf(a.get("ADDRESS")), cellFormat2);
                    Label lt3 = new Label(3, n, String.valueOf(a.get("FIRSTNAME")), cellFormat2);
                    Label lt4 = new Label(4, n, String.valueOf(a.get("LASTNAME")), cellFormat2);
                    sheet.addCell(lt0);
                    sheet.addCell(lt1);
                    sheet.addCell(lt2);
                    sheet.addCell(lt3);
                    sheet.addCell(lt4);
                    n++;
                }
            }

            //开始执行写入操作
            workbook.write();
            //关闭流
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 第六步，下载excel

        OutputStream out = null;
        try {

            // 1.弹出下载框，并处理中文
            /** 如果是从jsp页面传过来的话，就要进行中文处理，在这里action里面产生的直接可以用
             * String filename = request.getParameter("filename");
             */
            /**
             if (request.getMethod().equalsIgnoreCase("GET")) {
             filename = new String(filename.getBytes("iso8859-1"), "utf-8");
             }
             */

            response.addHeader("content-disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(filename, "utf-8"));

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
            e.printStackTrace();
        }
    } 
 
}
