package com.example.fullstackSpringBoot.exception;

public class Forbidden extends Exception { // Không có quyền
	public Forbidden(String message) {
		super(message);
	}
}
