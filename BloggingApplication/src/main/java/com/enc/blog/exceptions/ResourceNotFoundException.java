package com.enc.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException() {
		super("Resource with given Id is not present");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
