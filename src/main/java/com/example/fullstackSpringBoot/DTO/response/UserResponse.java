package com.example.fullstackSpringBoot.DTO.response;

public record UserResponse(
	String name,
	String phoneNumber,
	String address,
	String avt,
	long balance,
	String role) {
}
