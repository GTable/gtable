package com.example.gtable.global.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	private SecretKey secretKey;

	// 시크릿 키를 암호화하여, 키 생성
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(
			secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm()
		);
	}

	public String createAccessToken(String tokenCategory, Long userId, String role, Long expiredMs) {
		return Jwts.builder()
			.claim("tokenCategory", tokenCategory) // accessToken
			.claim("userId", userId)
			.claim("role", role)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}

	public String createRefreshToken(String tokenCategory, Long userId, Long expiredMs) {
		return Jwts.builder()
			.claim("tokenCategory", tokenCategory) // refreshToken
			.claim("userId", userId)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}

	public String getTokenCategory(String token) {
		return Jwts.parser().setSigningKey(secretKey).build()
			.parseClaimsJws(token)
			.getBody()
			.get("tokenCategory", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser().setSigningKey(secretKey).build()
			.parseClaimsJws(token)
			.getBody()
			.get("role", String.class);
	}

	public Long getUserId(String token) {
		return Jwts.parser().setSigningKey(secretKey).build()
			.parseClaimsJws(token)
			.getBody()
			.get("userId", Long.class);
	}

	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

}
