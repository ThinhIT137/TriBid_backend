package com.example.fullstackSpringBoot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Value("${spring.nextjs.link:http://localhost:3000}")
	private String nextJsLink;

	// Cấu hình RestFull API cho NextJs
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry
				.addMapping("/**")
				.allowedOrigins(nextJsLink)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true);
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
