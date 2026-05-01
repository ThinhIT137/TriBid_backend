package com.example.fullstackSpringBoot.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.fullstackSpringBoot.DTO.request.ChangePasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.ResetPasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.SignInRequest;
import com.example.fullstackSpringBoot.DTO.request.SignUpRequest;
import com.example.fullstackSpringBoot.DTO.response.AuthResponse;
import com.example.fullstackSpringBoot.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	// Đăng nhập
	AuthResponse SignIn(SignInRequest req, HttpServletResponse response);

	// Đăng ký
	AuthResponse SignUp(SignUpRequest req, HttpServletResponse response);

	// làm mới token
	AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken);

	// Đổi mâtj khẩu
	AuthResponse changePassword(@AuthenticationPrincipal User user, ChangePasswordRequest req,
			HttpServletResponse response);

	// Đăng xuất
	void logout(@AuthenticationPrincipal User user);

	// Quên mật khẩu
	void forgot(String email);

	// Đặt lại mật khẩu
	AuthResponse resetPassword(ResetPasswordRequest req, HttpServletResponse response);
}
