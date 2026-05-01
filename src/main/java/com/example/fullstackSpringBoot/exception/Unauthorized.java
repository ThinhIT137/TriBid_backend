package com.example.fullstackSpringBoot.exception;

public class Unauthorized extends Exception { // token hết hạn
	public Unauthorized(String message) {
		super(message);
	}
}
