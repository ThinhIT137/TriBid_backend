package com.example.fullstackSpringBoot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.example.fullstackSpringBoot.DTO.request.ChangePasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.ForgotRequest;
import com.example.fullstackSpringBoot.DTO.request.ResetPasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.SignInRequest;
import com.example.fullstackSpringBoot.DTO.request.SignUpRequest;
import com.example.fullstackSpringBoot.DTO.response.ApiResponse;
import com.example.fullstackSpringBoot.model.User;
import com.example.fullstackSpringBoot.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody SignInRequest req, HttpServletResponse response) {
		var accessToken = authService.SignIn(req, response);
		return ResponseEntity.ok(ApiResponse.success(accessToken, "Thành công"));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignUpRequest req, HttpServletResponse res) {
		var accessToken = authService.SignUp(req, res);
		return ResponseEntity.ok(ApiResponse.success(accessToken, "Thành công"));
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = WebUtils.getCookie(req, "refreshToken");
		String refreshToken = cookie != null ? cookie.getValue() : null;
		var accessToken = authService.refreshToken(req, res, refreshToken);
		return ResponseEntity.ok(ApiResponse.success(accessToken, "Thành công"));

	}

	@PostMapping("changePassword")
	public ResponseEntity<?> changePassword(@AuthenticationPrincipal User user, @RequestBody ChangePasswordRequest req,
			HttpServletResponse res) {
		var accessToken = authService.changePassword(user, req, res);
		return ResponseEntity.ok(ApiResponse.success(accessToken, "Thành công"));
	}

	@PostMapping("logout")
	@PreAuthorize("isAuthenticated")
	public ResponseEntity<?> logout(@AuthenticationPrincipal User user) {
		authService.logout(user);
		return ResponseEntity.ok(ApiResponse.success("Thành công"));
	}

	@PostMapping("forgot")
	public ResponseEntity<?> forgot(@RequestBody ForgotRequest req) {
		authService.forgot(req.email());
		return ResponseEntity.ok(ApiResponse.success("Đã gửi email"));
	}

	@PostMapping("resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req, HttpServletResponse res) {
		var accessToken = authService.resetPassword(req, res);
		return ResponseEntity.ok(ApiResponse.success(accessToken, "Thành công"));
	}
}
