package com.javabrains.springblog.exception;

public class PostNotFoundException extends RuntimeException{
	
	public PostNotFoundException(String message) {
		super(message);
	}

}
