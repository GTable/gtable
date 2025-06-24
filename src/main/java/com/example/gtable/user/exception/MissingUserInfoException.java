package com.example.gtable.user.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class MissingUserInfoException extends RuntimeException {
	public MissingUserInfoException() {
		super(ErrorMessage.MISSING_USER.getMessage());
	}
}
