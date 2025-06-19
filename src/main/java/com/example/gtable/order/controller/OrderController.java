package com.example.gtable.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.order.dto.OrderCreateRequestDto;
import com.example.gtable.order.dto.OrderCreateResponseDto;
import com.example.gtable.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API", description = "주문 API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/create/{storeId}/{tableId}")
	@Operation(summary = "주문 생성", description = "특정 주점 - 특정 테이블에 대한 주문 생성")
	@ApiResponse(responseCode = "201", description = "북마크 생성")
	public ResponseEntity<?> createOrder(
		@PathVariable Long storeId,
		@PathVariable Long tableId,
		@RequestBody OrderCreateRequestDto orderCreateRequestDto
		) {
		OrderCreateResponseDto response = orderService.createOrder(storeId,tableId,orderCreateRequestDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}
}
