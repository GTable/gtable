package com.example.gtable.orderitem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
	private Long menuId;           // 메뉴 ID
	private String menuName;       // 메뉴 이름
	private int quantity;          // 수량
}
