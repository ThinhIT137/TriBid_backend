package com.example.fullstackSpringBoot.DTO.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResetPasswordRequest(
	UUID user_id,
	String password,
	String key,
	LocalDateTime reseTime) {
}
