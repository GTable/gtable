package com.example.gtable.order.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class OrderParameterEmptyException extends RuntimeException {
	public OrderParameterEmptyException() {
		super(ErrorMessage.ORDER_PARAMETER_EMPTY.getMessage());
	}
}