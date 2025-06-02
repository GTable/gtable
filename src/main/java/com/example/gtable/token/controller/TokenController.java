package com.example.gtable.token.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.security.jwt.JwtUtil;
import com.example.gtable.token.dto.AuthenticationResponse;
import com.example.gtable.token.dto.RefreshTokenRequest;
import com.example.gtable.token.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
@Slf4j
public class TokenController {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    @PostMapping
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();

        // 리프레시 토큰 검증
        Long userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // 리프레시 토큰 유효성 검증
        if (tokenService.validateToken(refreshToken, userId)){
            // 유효한 토큰이라면, 새로운 accessToken, refreshToken 생성
            String newAccessToken = jwtUtil.createAccessToken("accessToken", userId, role, 30 * 60 * 1000L);
            String newRefreshToken = jwtUtil.createRefreshToken("refreshToken", userId, 30 * 24 * 60 * 60 * 1000L);

            // DB에 새로운 refreshToken으로 교체
            tokenService.updateRefreshToken(userId, refreshToken, newRefreshToken);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse(newAccessToken, refreshToken);
            return ResponseEntity.ok().body(authenticationResponse);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
    }
}
