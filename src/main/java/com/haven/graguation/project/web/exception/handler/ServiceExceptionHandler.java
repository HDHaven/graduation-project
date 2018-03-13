package com.haven.graguation.project.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.haven.graguation.project.web.exception.SourceModifyException;
import com.haven.graguation.project.web.exception.SourceNoContentException;
import com.haven.graguation.project.web.exception.SourceNotFoundException;
import com.haven.graguation.project.web.exception.SourceRemoveException;
import com.haven.graguation.project.web.exception.SourceSaveException;

/**
 * 全局异常处理类
 * 
 * @author Haven
 * @date 2018/03/13
 */
@ControllerAdvice
public class ServiceExceptionHandler {

	@ExceptionHandler(SourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody Error sourceNotFound(SourceNotFoundException e) {
		Long id = e.getId();
		String className = e.getClassName();
		return new Error(404, className +"["+ id +"] not found ");
	}
	
	@ExceptionHandler(SourceSaveException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Error sourceSaveFailure(SourceSaveException e) {
		Object obj = e.getObj();
		return new Error(500, obj.getClass().getName() +"{"+ obj.toString() +"} save failure");
	}
	
	@ExceptionHandler(SourceRemoveException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Error sourceRemoveFailure(SourceRemoveException e) {
		Long id = e.getId();
		String sourceName = e.getSourceName();
		return new Error(500, sourceName +"["+ id +"] remove failure ");
	}
	
	@ExceptionHandler(SourceModifyException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody Error sourceModifyFailure(SourceModifyException e) {
		Long id = e.getId();
		String sourceName = e.getSourceName();
		return new Error(500, sourceName +"["+ id +"] update failure ");
	}
	
	@ExceptionHandler(SourceNoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody Error sourceEmpty(SourceNoContentException e) {
		return new Error(204, e.getObj().toString() +"search result is empty ");
	}
	
}
