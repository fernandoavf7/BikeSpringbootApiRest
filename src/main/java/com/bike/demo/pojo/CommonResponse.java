package com.bike.demo.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private StatusResponse status; 
	private Object objectResponse;
}
