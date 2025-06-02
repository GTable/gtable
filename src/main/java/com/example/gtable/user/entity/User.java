package com.example.gtable.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 카카오 이메일

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String nickname, String profileImage, SocialType socialType, Role role){
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.socialType = socialType;
        this.role = role;
    }

    public static User createUserWithId(Long userId, String email, String nickname, String profileImage, SocialType socialType, Role role){
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .profileImage(profileImage)
                .socialType(socialType)
                .role(role)
                .build();
        user.id = userId;

        return user;
    }

    // User 도메인 관련 비즈니스 로직 (예: 닉네임 변경)
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}
