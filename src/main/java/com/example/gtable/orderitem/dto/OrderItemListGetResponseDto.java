package com.example.gtable.orderitem.dto;

import com.example.gtable.orderitem.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemListGetResponseDto {
	private Long orderId;
	private String menuName;
	private Integer quantity;
	private Integer price;

	public static OrderItemListGetResponseDto fromEntity(OrderItem orderItem) {
		return OrderItemListGetResponseDto.builder()
			.orderId(orderItem.getUserOrder().getId())
			.menuName(orderItem.getMenu().getName())
			.quantity(orderItem.getQuantity())
			.price(orderItem.getMenu().getPrice())
			.build();

	}
}
