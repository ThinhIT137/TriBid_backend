package com.example.fullstackSpringBoot.exception;

public class BadRequest extends Exception { // gửi dữ liệu thiếu /sai
	public BadRequest(String message) {
		super(message);
	}
}
