package com.example.gtable.global.security.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class RefreshTokenNotFoundException extends ResourceNotFoundException {

	public RefreshTokenNotFoundException() {
		super(ErrorMessage.valueOf(ErrorMessage.REFRESH_TOKEN_NOT_FOUND.getMessage()));
	}

}
