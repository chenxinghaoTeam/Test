package lambda;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class weather {
	public static void main(String[] args) {
		String param = "version=v1";
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		BufferedReader br = null;
		PrintWriter out = null;
		try {
		    //接口地址
		    String url = "https://www.tianqiapi.com/api/";
		    URL    uri = new URL(url);
		    HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setReadTimeout(5000);
		    connection.setConnectTimeout(10000);
		    connection.setRequestProperty("accept", "*/*");
		    //发送参数
		    connection.setDoOutput(true);
		    out = new PrintWriter(connection.getOutputStream());
		    out.print(param);
		    out.flush();
		    //接收结果
		    is = connection.getInputStream();
		    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    String line;
		    //缓冲逐行读取
		    while ( ( line = br.readLine() ) != null ) {
		        sb.append(line);
		    }
		    System.out.println(sb.toString());
		} catch ( Exception ignored ) {
		} finally {
		    //关闭流
		    try {
		        if(is!=null){
		            is.close();
		        }
		        if(br!=null){
		            br.close();
		        }
		        if (out!=null){
		            out.close();
		        }
		    } catch ( Exception ignored ) {}
		}
	}

}
