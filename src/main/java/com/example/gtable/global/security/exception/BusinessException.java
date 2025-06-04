package com.example.gtable.global.security.exception;

public abstract class BusinessException extends RuntimeException {
	private final ErrorMessage errorMessage;

	protected BusinessException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return errorMessage.getCode();
	}
}
