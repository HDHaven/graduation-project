package com.haven.graguation.project.repository.mybatis.exception;

import com.haven.graguation.project.repository.mybatis.annotation.PrimaryKey;

/**
 * @author Haven
 * @date 2018/03/12
 */
public class PKNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PKNotFoundException(Class<?> clazz) {
		super("There is no annotation [ "+ PrimaryKey.class.getName() +" ] be used in [ "+ clazz.getName() +" ] 's field!");
	}
	
}
