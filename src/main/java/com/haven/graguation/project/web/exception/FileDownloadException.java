package com.haven.graguation.project.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownloadException extends RuntimeException {
	private static final long serialVersionUID = -3740065906360334471L;
	
	private static final Logger logger = LoggerFactory.getLogger(FileDownloadException.class);
	
	public FileDownloadException() {
	}
	
	public FileDownloadException(String msg) {
		super(msg);
		logger.error(msg);
	}
	
	// TODO
	
}
