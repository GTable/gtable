package com.example.gtable.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderCreateRequestDto {
	private final List<CartItemDto> items; // 장바구니 항목 리스트

}
