package com.example.gtable.token.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.global.security.exception.RefreshTokenNotFoundException;
import com.example.gtable.global.security.exception.TokenBadRequestException;
import com.example.gtable.global.security.jwt.JwtUtil;
import com.example.gtable.token.entity.Token;
import com.example.gtable.token.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Boolean validateToken(String token, Long userId){
        // DB에서 해당 userId와 일치하는 리프레시토큰을 찾는다.
        Optional<Token> savedToken = tokenRepository.findByUserId(userId);
        
        // DB에서 userId에 대응되는 리프레시토큰 없으면, 유효하지 않음
        if (savedToken.isEmpty()){
            log.info("여기에 걸렸니 ? -- 1 ");
            return false;
        }
        
        // 리프레시 토큰이 DB에 저장된 토큰과 일치하는지 확인
        if (!savedToken.get().getRefreshToken().equals(token)){
            log.info("여기에 걸렸니 ? -- 2 ");
            return false;
        }
        
        // 리프레시 토큰의 만료여부 확인
        if(jwtUtil.isExpired(token)){
            log.info("여기에 걸렸니 ? -- 3 ");
            return false; // 만료된 토큰은 유효하지 않음
        }

        log.info("여기에 걸렸니 ? -- 4 ");
        return true; // 모든 조건 만족 시, 유효한 토큰
    }

    @Transactional
    public void updateRefreshToken(Long userId, String oldRefreshToken, String newRefreshToken){
        Token token = tokenRepository.findByUserId(userId)
                .orElseThrow(RefreshTokenNotFoundException::new); // 404

        if (!token.getRefreshToken().equals(oldRefreshToken)){
            throw new TokenBadRequestException(); // 400
        }

        // 기존 토큰 삭제 및 새 토큰 저장
        tokenRepository.delete(token);
        Token newToken = Token.toEntity(token.getUser(), newRefreshToken, LocalDateTime.now().plusDays(30));
        tokenRepository.save(newToken);
    }
}
