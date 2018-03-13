package com.haven.graguation.project.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadException extends RuntimeException {
	private static final long serialVersionUID = 8050676109624315088L;

	private static final Logger logger = LoggerFactory.getLogger(FileUploadException.class);
	
	public FileUploadException() {
	}

	public FileUploadException(String msg) {
		super(msg);
		logger.error(msg);
	}
	
}
