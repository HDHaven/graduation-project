package com.haven.graguation.project.web.exception;

public class SourceNoContentException extends RuntimeException {
	private static final long serialVersionUID = -8691161905903955137L;

	private Object obj;
	
	public SourceNoContentException() {}
	
	public SourceNoContentException(Object obj) {
		this.obj = obj;
	}

	public Object getObj() {
		return obj;
	}
	
}
