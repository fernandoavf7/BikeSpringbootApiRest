package com.bike.demo.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse implements Serializable {

	/*
	 * this attribute is an id for serializable classes, it allow avoid possible
	 * errors
	 */
	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusMessage;
}
