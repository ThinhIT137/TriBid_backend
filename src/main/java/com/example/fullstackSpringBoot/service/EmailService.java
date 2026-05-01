package com.example.fullstackSpringBoot.service;

import jakarta.mail.MessagingException;

public interface EmailService {
	void sendVerifyEmail(String to, String Subject, String text) throws MessagingException; // Gửi email
}
