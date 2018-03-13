package com.haven.graguation.project.web.exception;

public class SourceRemoveException extends RuntimeException {
	private static final long serialVersionUID = -9212733880612658930L;

	private Long id;
	private String sourceName;
	
	public SourceRemoveException() {}

	public SourceRemoveException(Long id, String sourceName) {
		this.id = id;
		this.sourceName = sourceName;
	}

	public Long getId() {
		return id;
	}

	public String getSourceName() {
		return sourceName;
	}
	
}
