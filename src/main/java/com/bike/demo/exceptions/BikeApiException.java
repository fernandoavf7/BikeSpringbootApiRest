package com.bike.demo.exceptions;

public class BikeApiException extends Exception{

	private static final long serialVersionUID = 1L;

	public BikeApiException(String message) {
        super(message);
    }
}
