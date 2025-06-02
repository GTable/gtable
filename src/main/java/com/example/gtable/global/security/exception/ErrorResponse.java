package com.example.gtable.global.security.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
	private String message;
	private String code;
	private Map<String, String> errors;

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

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

}
