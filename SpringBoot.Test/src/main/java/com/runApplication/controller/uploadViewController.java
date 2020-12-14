package com.runApplication.controller;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class uploadViewController {
	public static Logger log = LogManager.getLogger(uploadViewController.class);
	
	@RequestMapping("/upload")
	  public String upload(@RequestParam("file") MultipartFile file) {
	        if (file.isEmpty()) {
	            return "上传失败，请选择文件";
	        }

	        String fileName = file.getOriginalFilename();
	        String filePath = "F:/上传";
	        File dest = new File(filePath + fileName);
	        try {
	            file.transferTo(dest);
	            log.info("上传成功");
	            return "上传成功";
	        } catch (IOException e) {
	        	log.error(e.toString(), e);
	        }
	        return "上传失败！";
	    }
	 
	
}
