package com.example.fullstackSpringBoot.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.fullstackSpringBoot.DTO.request.ChangePasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.ResetPasswordRequest;
import com.example.fullstackSpringBoot.DTO.request.SignInRequest;
import com.example.fullstackSpringBoot.DTO.request.SignUpRequest;
import com.example.fullstackSpringBoot.DTO.response.AuthResponse;
import com.example.fullstackSpringBoot.model.User;
import com.example.fullstackSpringBoot.repository.RefreshTokenRepository;
import com.example.fullstackSpringBoot.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtService jwtService;
	private final EmailService emailService;

	@Value("${spring.nextjs.link:http://localhost:3000}")
	private String nextJsLink;

	@Override
	public AuthResponse SignIn(SignInRequest req, HttpServletResponse response) {
		var u = userRepository.findByEmail(req.email()).orElse(null);

		if (u == null) { // người dùng không tồn tại
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sai email hoặc mật khẩu!");
		}

		if (!BCrypt.checkpw(req.password(), u.getPassword())) { // sai mật khẩu
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Sai mật khẩu");
		}
		jwtService.revokeUserTokens(u);
		var accessToken = jwtService.createAccessToken(u);
		var refreshToken = jwtService.createRefreshToken(u);

		CookieRefreshToken(refreshToken, response);
		return new AuthResponse(accessToken);
	}

	@Override
	public AuthResponse SignUp(SignUpRequest req, HttpServletResponse response) {
		var u = userRepository.findByEmail(req.email()).orElse(null);

		if (u != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Người dùng đã tồn tại");
		}
		var user = new User(req.email(), BCrypt.hashpw(req.password(), BCrypt.gensalt()), LocalDateTime.now());
		userRepository.save(user);
		var accessToken = jwtService.createAccessToken(user);
		var refreshToken = jwtService.createRefreshToken(user);

		CookieRefreshToken(refreshToken, response);
		return new AuthResponse(accessToken);
	}

	@Override
	public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không có refreshToken");
		}
		var authHeader = request.getHeader("Authorization");
		var token = "";
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}
		var user = jwtService.extractUsername(token);
		if (refreshTokenRepository.findByTokenAndUser_Id(refreshToken, user.getId()).orElse(null) == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "refresh token không đúng định dạng");
		}
		jwtService.revokeUserTokens(user);
		var accessToken = jwtService.createAccessToken(user);
		var RefreshToken = jwtService.createRefreshToken(user);
		CookieRefreshToken(RefreshToken, response);
		return new AuthResponse(accessToken);
	}

	@Override
	public AuthResponse changePassword(@AuthenticationPrincipal User user, ChangePasswordRequest req,
			HttpServletResponse response) {
		user.setPassword(BCrypt.hashpw(req.password(), BCrypt.gensalt()));
		userRepository.save(user);
		jwtService.revokeUserTokens(user);
		var accessToken = jwtService.createAccessToken(user);
		var refreshToken = jwtService.createRefreshToken(user);
		CookieRefreshToken(refreshToken, response);
		return new AuthResponse(accessToken);
	}

	@Override
//	@PreAuthorize("isAuthenticated()")
	public void logout(@AuthenticationPrincipal User user) {
		jwtService.revokeUserTokens(user);
	}

	@Override
	public void forgot(String email) {
		var user = userRepository.findByEmail(email).orElse(null);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tài khoản không tồn tại");

		String Subject = "<h1>Đổi mật khẩu</h1>";
		String Text = "" + "<p>Click vào đây để đổi mật khẩu, thời hạn đổi trong 15p</p>" + "<a href=\"" + nextJsLink
				+ "\">Đổi mật khẩu</a>";
		try {
			emailService.sendVerifyEmail(email, Subject, Text);
		} catch (MessagingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Gửi email thất bại vui lòng gửi lại sau");
		}
	}

	@Override
	public AuthResponse resetPassword(ResetPasswordRequest req, HttpServletResponse response) {
		var user = userRepository.findById(req.user_id()).orElse(null);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Đổi mật khẩu thất bại do người dùng không tồn tại!");
		}

		if (!user.getKeyResetPassword().contains(req.key())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mã đổi mật khẩu gửi lên không đúng!");
		}

		if (!user.getResetPasswordAt().isBefore(req.reseTime())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quá hạn đổi mật khẩu!");
		}
		jwtService.revokeUserTokens(user);
		var accessToken = jwtService.createAccessToken(user);
		var refreshToken = jwtService.createRefreshToken(user);
		CookieRefreshToken(refreshToken, response);
		return new AuthResponse(refreshToken);
	}

	private void CookieRefreshToken(String token, HttpServletResponse response) {
		ResponseCookie refreshToken = ResponseCookie
				.from("refreshToken", token)
				.httpOnly(true)
				.maxAge(Duration.ofDays(7))
				.secure(false)
				.path("/")
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());
	}
}
