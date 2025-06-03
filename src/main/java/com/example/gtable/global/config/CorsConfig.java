package com.example.gtable.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true); // 쿠키나 인증헤더 자격증명 허용
		config.setAllowedOrigins(List.of("http://localhost:3000")); // 허용할 출처 설정
		config.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")); // 메서드 허용
		config.setAllowedHeaders(List.of("*")); //클라이언트가 보낼 수 있는 헤더
		config.setExposedHeaders(List.of("Authorization")); //클라이언트(브라우저)가 접근할 수 있는 헤더 지정
		//  config.setAllowCredentials(true); // 쿠키 포함 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config); //** 뜻은 모든 URL 경로에 적용한다는 의미
		return source;
	}
}
