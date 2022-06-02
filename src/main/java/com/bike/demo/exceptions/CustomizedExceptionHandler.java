package com.bike.demo.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bike.demo.pojo.CommonResponse;
import com.bike.demo.pojo.StatusResponse;
import com.bike.demo.util.ResponseUtils;

/*controllerAdvice is an implementation of @component, its used for classes that will be shared for several controller classes */
@ControllerAdvice
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ResponseUtils utils;

	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<CommonResponse> dividedByZeroExceptionHandler(MethodArgumentTypeMismatchException ex) {

		CommonResponse response;
		StatusResponse messageResponse = new StatusResponse();

		messageResponse
				.setStatusMessage("There is an error trying to process your request, please review your request");
		messageResponse.setStatusCode("500");
		response = utils.mapToCommonResponse(messageResponse, null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BikeApiException.class)
	public ResponseEntity<CommonResponse> bikeApiException(BikeApiException ex) {

		CommonResponse response;
		StatusResponse messageResponse = new StatusResponse();

		messageResponse
				.setStatusMessage(ex.getMessage());
		messageResponse.setStatusCode("500");
		response = utils.mapToCommonResponse(messageResponse, null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
