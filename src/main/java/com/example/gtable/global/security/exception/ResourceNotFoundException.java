package com.example.gtable.global.security.exception;

import com.example.gtable.global.exception.ErrorMessage;

public abstract class ResourceNotFoundException extends RuntimeException {
	private final ErrorMessage errorMessage;

	protected ResourceNotFoundException(String errorMessage) {
		super(errorMessage.getMessage());
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return errorMessage.getCode();
	}
}
