package com.enc.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enc.blog.payloads.ResourceNotFoundExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResourceNotFoundExceptionResponse> userNotFoundExceptionHandler( ResourceNotFoundException ex) {
		ResourceNotFoundExceptionResponse response = ResourceNotFoundExceptionResponse.builder().message(ex.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();
		ResponseEntity<ResourceNotFoundExceptionResponse> exceptionResponse = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		return exceptionResponse;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		Map<String, String> exceptionResponse = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName =((FieldError) error).getField();
			String message = error.getDefaultMessage();
			exceptionResponse.put(fieldName, message);
		});
		return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
	}

}
