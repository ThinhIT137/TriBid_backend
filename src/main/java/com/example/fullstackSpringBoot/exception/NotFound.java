package com.example.fullstackSpringBoot.exception;

public class NotFound extends Exception { // không tồn tại
	public NotFound(String message) {
		super(message);
	}
}
