package com.example.gtable.global.api;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiUtils {
	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response, null);
	}

	public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(throwable, status));
	}

	public static ApiResult<?> error(String message, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(message, status));
	}
}
