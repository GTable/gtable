package com.example.gtable.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
	// global
	INVALID_INPUT_VALUE("입력값이 올바르지 않습니다.", "global001"),

	// auth
	UNAUTHORIZED("권한이 없습니다", "auth001"),

	// token
	REFRESH_TOKEN_NOT_FOUND("기존 리프레시 토큰을 찾을 수 없습니다.", "token001"),
	DOES_NOT_MATCH_REFRESH_TOKEN("기존 리프레시 토큰이 일치하지 않습니다.", "token002"),

	// user
	MISSING_USER("사용자 정보가 없습니다.", "user001"),
	// bookmark
	DUPLICATE_BOOKMARK("이미 북마크한 주점입니다.", "bookmark001"),
	NOT_OWN_BOOKMARK("권한이 없습니다.", "bookmark002");

	private final String message;
	private final String code;

}
