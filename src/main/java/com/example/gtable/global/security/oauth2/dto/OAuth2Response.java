package com.example.gtable.global.security.oauth2.dto;

public interface OAuth2Response {
	// 제공자 (ex. naver, kakao)
	String getProvider();

	// 제공자에서 발급해주는 아이디 (번호)
	String getProviderId();

	// 아래 이메일, 닉네임, 프로필이미지는 내가 카카오 developers에서 발급받겠다고 신청한 정보들이다
	// 이메일
	String getEmail();

	// 닉네임
	String getNickName();

	// 프로필이미지
	String getProfileImage();
}
