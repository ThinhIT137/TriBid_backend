package com.example.fullstackSpringBoot.service;

import com.example.fullstackSpringBoot.model.User;

public interface JwtService {
	// Tạo
	public String createAccessToken(User user);

	public String createRefreshToken(User user);

	// Lưu & hủy
	public void saveToken(User user, String refreshToken);

	public void revokeUserTokens(User user);

	// Tìm
	public User extractUsername(String token);

	// Kiểm tra token hợp lệ
//	boolean isTokenValid(String token);
}
