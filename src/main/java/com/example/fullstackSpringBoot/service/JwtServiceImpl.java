package com.example.fullstackSpringBoot.service;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.fullstackSpringBoot.model.RefreshToken;
import com.example.fullstackSpringBoot.model.User;
import com.example.fullstackSpringBoot.repository.RefreshTokenRepository;
import com.example.fullstackSpringBoot.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	@Value("${spring.secretkey}")
	private String secretKey;
	@Value("${spring.accesstoken-token-expiration}")
	private Duration ACCESS_TOKEN_EXPIRATION;
	@Value("${spring.refresh-token-expiration}")
	private Duration REFRESH_TOKEN_EXPIRATION;

	@Override
	public String createAccessToken(User user) {
		Map<String, Object> extraClaims = Map.of("id", user.getId(), "email", user.getEmail());
		return buildToken(extraClaims, user, ACCESS_TOKEN_EXPIRATION);
	}

	private String buildToken(Map<String, Object> extraClaims, User user, Duration expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getId().toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getKey() {
		byte[] keyBytes = secretKey.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String createRefreshToken(User user) {
		var refreshToken = UUID.randomUUID().toString();
		saveToken(user, refreshToken);
		return refreshToken;
	}

	@Override
	public void saveToken(User user, String refreshToken) {
		refreshTokenRepository
				.save(new RefreshToken(refreshToken, LocalDateTime.now(),
						LocalDateTime.now().plus(REFRESH_TOKEN_EXPIRATION), true, user));
	}

	@Override
	public void revokeUserTokens(User user) {
		refreshTokenRepository.revokeAllTokensByUserId(user.getId());
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
	}

	@Override
	public User extractUsername(String token) {
		var id = extractAllClaims(token).getSubject();
		var user = userRepository.findById(UUID.fromString(id));

		return user.orElse(null);
	}
}
