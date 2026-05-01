package com.example.fullstackSpringBoot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.example.fullstackSpringBoot.DTO.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandle {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
		ex.printStackTrace();
		return ResponseEntity.status(ex.getStatusCode()).body(ApiResponse.error(ex.getReason()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.error("Hệ thống đang gặp sự cố xin thông cảm!"));
	}

	@ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwtException(Exception ex) {
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Token không đúng định dạng"));
	}
}
