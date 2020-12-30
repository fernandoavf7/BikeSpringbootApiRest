package com.bike.demo.response;

import java.time.LocalDateTime;

public class CommonResponse {

	private String code;
	private String message;
	private LocalDateTime date;
	private Object objResponse;
	
	public CommonResponse() {
		super();
		this.date = LocalDateTime.now();
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Object getObjResponse() {
		return objResponse;
	}

	public void setObjResponse(Object objResponse) {
		this.objResponse = objResponse;
	}
	
	
	
}
