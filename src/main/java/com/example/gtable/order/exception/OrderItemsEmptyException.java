package com.example.gtable.order.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class OrderItemsEmptyException extends RuntimeException {
	public OrderItemsEmptyException() {
		super(ErrorMessage.ORDER_ITEMS_EMPTY.getMessage());
	}
}