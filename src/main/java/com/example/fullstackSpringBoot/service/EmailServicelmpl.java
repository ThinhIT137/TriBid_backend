package com.example.fullstackSpringBoot.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServicelmpl implements EmailService {
	private final JavaMailSender mailSender;

	@Override
	public void sendVerifyEmail(String to, String Subject, String text) throws MessagingException {
		MimeMessage messenger = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(messenger, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(Subject);
		helper.setText(text);
		mailSender.send(messenger);
	}
}
