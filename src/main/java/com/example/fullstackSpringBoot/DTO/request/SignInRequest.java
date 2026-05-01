package com.example.fullstackSpringBoot.DTO.request;

public record SignInRequest(
	String email,
	String password) {
}
