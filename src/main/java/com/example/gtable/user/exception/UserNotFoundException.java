package com.example.gtable.user.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {
		super(ErrorMessage.NOTFOUND_USER.getMessage());
	}
}
