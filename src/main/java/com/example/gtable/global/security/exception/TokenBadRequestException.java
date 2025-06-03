package com.example.gtable.global.security.exception;

public class TokenBadRequestException extends BusinessException {
	public TokenBadRequestException() {
		super(ErrorMessage.DOES_NOT_MATCH_REFRESH_TOKEN);
	}

}
