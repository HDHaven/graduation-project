package com.haven.graguation.project.web.exception;

public class SourceSaveException extends RuntimeException {
	private static final long serialVersionUID = 7092406667918669836L;

	private Object obj;
	
	public SourceSaveException() {}
	
	public SourceSaveException(Object obj) {
		this.obj = obj;
	}
	
	public Object getObj() {
		return obj;
	}
	
}
