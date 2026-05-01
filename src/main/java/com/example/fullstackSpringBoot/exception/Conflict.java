package com.example.fullstackSpringBoot.exception;

public class Conflict extends Exception { // Xung đột dữ liệu
	public Conflict(String message) {
		super(message);
	}
}
