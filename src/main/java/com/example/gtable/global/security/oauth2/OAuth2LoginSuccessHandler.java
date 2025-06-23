package com.example.gtable.global.security.oauth2;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.gtable.global.security.jwt.JwtUtil;
import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.token.entity.Token;
import com.example.gtable.token.repository.TokenRepository;
import com.example.gtable.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 카카오 로그인 성공 시, 콜백 핸들러
// 1. JWT 토큰 발급
// - 이때, JWT payload는 보안상 최소한의 정보(userId, role)만 담겠다
// 2. refreshToken만 DB에 저장
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtUtil jwtUtil;
	private final TokenRepository tokenRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
		User user = customUserDetails.getUser();
		Long userId = customUserDetails.getUserId();
		String role = authentication.getAuthorities().iterator().next().getAuthority();

		// JWT 발급
		String accessToken = jwtUtil.createAccessToken("accessToken", userId, role, 30 * 60 * 1000L); // 30분
		String refreshToken = jwtUtil.createRefreshToken("refreshToken", userId, 30L * 24 * 60 * 60 * 1000L); // 30일

		// 1. refreshToken을 DB에 저장
		Token refreshTokenEntity = Token.toEntity(user, refreshToken, LocalDateTime.now().plusDays(30));
		tokenRepository.save(refreshTokenEntity);

		// 2. refreshToken을 HttpOnly 쿠키로 설정
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); // JS 접근 불가
		refreshTokenCookie.setSecure(false); // 운영환경 https라면 true
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60); // 30일
		response.addCookie(refreshTokenCookie);

		// 3. 프론트엔드로 리다이렉트 (accessToken만 쿼리로 전달)
		String targetUrl = "http://localhost:5173/login/success?accessToken=" + accessToken;
		response.sendRedirect(targetUrl);
	}

}
