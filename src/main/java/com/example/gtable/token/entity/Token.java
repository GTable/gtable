package com.example.gtable.token.entity;

import java.time.LocalDateTime;

import com.example.gtable.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
@Getter
public class Token {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String refreshToken;

    private LocalDateTime expiredDate;

    @Builder
    public Token(User user, String refreshToken, LocalDateTime expiredDate) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.expiredDate = expiredDate;
    }

    // static method로 객체를 생성 - 생성 의도 파악 쉬웁
    public static Token toEntity(User user, String refreshToken, LocalDateTime expiredDate){
        return Token.builder()
                .user(user)
                .refreshToken(refreshToken)
                .expiredDate(expiredDate)
                .build();
    }

}
