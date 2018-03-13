package com.haven.graguation.project.web.exception;

public class SourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6394471563864026485L;

	private Long id;
	private String sourceName;
	
	public SourceNotFoundException() {}
	
	public SourceNotFoundException(Long id, String className) {
		this.id = id;
		this.sourceName = className;
	}

	public Long getId() {
		return id;
	}

	public String getClassName() {
		return sourceName;
	}
	
}
