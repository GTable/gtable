package com.example.gtable.global.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiError {
	private final String message;
	private final int status;

	public ApiError(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public ApiError(Throwable throwable, HttpStatus status) {
		this(throwable.getMessage(), status);
	}

	public ApiError(String message, HttpStatus status) {
		this(message, status.value());
	}
}
