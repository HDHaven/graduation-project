package com.haven.graguation.project.repository.mybatis.exception;

public class UpdateDomainException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UpdateDomainException(Class<?> clazz, String msg) {
		super("update [ "+ clazz.getName() +" ] exception: "+ msg);
	}
	
}
