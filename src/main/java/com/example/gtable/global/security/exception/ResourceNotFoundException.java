package com.example.gtable.global.security.exception;

public abstract class ResourceNotFoundException extends RuntimeException {
	private final ErrorMessage errorMessage;

	protected ResourceNotFoundException(ErrorMessage errorMessage) {
		super(errorMessage.getMessage());
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return errorMessage.getCode();
	}
}
