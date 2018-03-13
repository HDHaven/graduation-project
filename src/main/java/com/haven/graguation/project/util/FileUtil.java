package com.haven.graguation.project.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haven.graguation.project.web.exception.FileDownloadException;
import com.haven.graguation.project.web.exception.FileUploadException;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	private static final String BASE_PATH = "F:/basepath/";
	
	public static Boolean upload(String filePath, byte[] bytes, String fileName) throws FileUploadException {
		Boolean result = Boolean.FALSE;
		filePath = BASE_PATH + filePath;
		FileOutputStream fos = null;
		try {
			File file = new File(filePath);
			if(!file.exists())
				file.mkdirs();
			fos = new FileOutputStream(filePath + File.separator + fileName);
			fos.write(bytes);
			fos.flush();
			result = Boolean.TRUE;
		} catch(Exception e) {
			String msg = "upload file {"+ filePath+"/"+fileName +"} error: "+ e.getMessage();
			logger.error(msg, e);
			throw new FileUploadException(msg);
		} finally {
			if(fos != null) try { fos.close(); } catch(Exception e) {}
		}
		return result;
	}
	
	/**
	 * 文件下载
	 * 
	 * @param filePath
	 * @param fileName
	 * @param response
	 * @return
	 * @throws FileDownloadException
	 */
	public static Boolean download(String filePath, String fileName, HttpServletResponse response) throws FileDownloadException {
		Boolean result = Boolean.FALSE;
		filePath = BASE_PATH + filePath;
		OutputStream os = null;
		BufferedInputStream bis = null;
		try {
			File file = new File(filePath +File.separator+ fileName);
			if(!file.exists()) throw new FileDownloadException("The download file {"+ filePath+"/"+fileName +"} is not exist!");
			response.setHeader("content-type", "application/octet-stream");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
			os = response.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes, 0, bytes.length);
			os.write(bytes);
			os.flush();
			result = Boolean.TRUE;
		} catch(Exception e) {
			logger.error("download file {"+ filePath+"/"+fileName +"} error: "+ e.getMessage(), e);
			throw new FileDownloadException();
		} finally {
			if(bis != null) try { bis.close(); } catch(Exception e2) {}
		}
		return result;
	}
	
}
