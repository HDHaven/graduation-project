package com.haven.graguation.project.web.exception.handler;

/**
 * 封装状态码以及异常信息
 * 
 * @author Haven
 * @date 2018/03/13
 */
public class Error {

	private Integer code;
	
	private String msg;
	
	public Error() {}

	public Error(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
