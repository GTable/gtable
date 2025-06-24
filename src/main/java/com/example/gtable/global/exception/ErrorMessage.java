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
	NOTFOUND_USER("저장된 사용자 정보가 없습니다.", "user001"),

	//order
	ORDER_PARAMETER_EMPTY("주문 생성 시 파라미터 정보가 없습니다.", "order001"),
	ORDER_ITEMS_EMPTY("주문 항목이 없습니다.", "order002"),
	DUPLICATE_ORDER("동일한 주문이 접수되었습니다.", "order003"),

	//reservation
	NOTFOUND_RESERVATION("저장된 예약 정보가 없습니다.", "reservation001"),

	// bookmark
	DUPLICATE_BOOKMARK("이미 북마크한 주점입니다.", "bookmark001"),
	NOT_OWN_BOOKMARK("해당 주점은 다른 사용자가 북마크한 주점입니다.", "bookmark002");

	private final String message;
	private final String code;

}
