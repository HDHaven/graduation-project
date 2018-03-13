package com.haven.graguation.project.web.exception;

public class SourceModifyException extends RuntimeException {
	private static final long serialVersionUID = -1572116353683542873L;

	private Long id;
	private String sourceName;
	
	public SourceModifyException() {}
	
	public SourceModifyException(Long id, String sourceName) {
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
