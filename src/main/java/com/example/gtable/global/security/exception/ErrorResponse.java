package com.example.gtable.global.security.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private final String message;
	private final String code;
	private final Map<String, String> errors;

	public ErrorResponse(String message, String code) {
		this.message = message;
		this.code = code;
		errors = new HashMap<>();
	}

	public ErrorResponse(String message, String code, Map<String, String> errors) {
		this.message = message;
		this.code = code;
		this.errors = errors;
	}

}
