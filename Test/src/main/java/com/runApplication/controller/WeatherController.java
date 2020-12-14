package com.runApplication.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/QueryWeather")
public class WeatherController {

	@RequestMapping("/getWeather")
	public String QueryWeather() {
		String url = "http://wthrcdn.etouch.cn/weather_mini?city=浦口";
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		String param = URLEncodedUtils.format(params, "utf-8");
		HttpGet httpGet = new HttpGet(url + param);
		HttpClient httpClient = new DefaultHttpClient();
		String result = "";
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			result = getJsonStringFromGZIP(httpResponse);// 获取到解压缩之后的字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getJsonStringFromGZIP(HttpResponse response) {
		String jsonString = null;
		try {
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			bis.mark(2);
			// 取前两个字节
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int headerData = getShort(header);
			if (result != -1 && headerData == 0x1f8b) {
				is = new GZIPInputStream(bis);
			} else {
				is = bis;
			}
			InputStreamReader reader = new InputStreamReader(is, "utf-8");
			char[] data = new char[100];
			int readSize;
			StringBuffer sb = new StringBuffer();
			while ((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}
			jsonString = sb.toString();
			bis.close();
			reader.close();
		} catch (Exception e) {
		}
		return jsonString;
	}

	private int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}
}
