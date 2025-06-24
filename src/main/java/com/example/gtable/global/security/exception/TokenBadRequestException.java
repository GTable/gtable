package com.example.gtable.global.security.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class TokenBadRequestException extends BusinessException {
	public TokenBadRequestException() {
		super(ErrorMessage.valueOf(ErrorMessage.DOES_NOT_MATCH_REFRESH_TOKEN.getMessage()));
	}

}
