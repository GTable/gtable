package com.example.gtable.order.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class DuplicateOrderException extends RuntimeException {
	public DuplicateOrderException() {
		super(ErrorMessage.DUPLICATE_ORDER.getMessage());
	}
}
