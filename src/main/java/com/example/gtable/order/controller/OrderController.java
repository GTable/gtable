package com.example.gtable.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.order.dto.OrderCreateRequestDto;
import com.example.gtable.order.dto.OrderCreateResponseDto;
import com.example.gtable.order.service.OrderService;
import com.example.gtable.orderitem.dto.OrderItemListGetResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API", description = "주문 API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping("/create/{storeId}/{tableId}")
	@Operation(summary = "주문 생성", description = "특정 주점 - 특정 테이블에 대한 주문 생성")
	@ApiResponse(responseCode = "201", description = "주문 생성")
	public ResponseEntity<?> createOrder(
		@PathVariable Long storeId,
		@PathVariable Long tableId,
		@RequestBody @Valid OrderCreateRequestDto orderCreateRequestDto,
		HttpSession session
		) {
		String sessionId = session.getId();
		OrderCreateResponseDto response = orderService.createOrder(storeId,tableId,orderCreateRequestDto,sessionId);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(response)
			);
	}

	@GetMapping("/items/{storeId}/{tableId}")
	@Operation(summary = "테이블별 주문 아이템 조회", description = "비로그인(세션) 기준으로 테이블의 내 주문 목록만 조회")
	@ApiResponse(responseCode = "200", description = "주문 조회")
	public ResponseEntity<?> getOrderItems(
		@PathVariable Long storeId,
		@PathVariable Long tableId,
		HttpSession session
	) {
		// 세션ID 추출 (Spring이 세션 자동 관리)
		String sessionId = session.getId();

		List<OrderItemListGetResponseDto> orderItems = orderService.getOrderItems(storeId, tableId, sessionId);
		return ResponseEntity.
			status(HttpStatus.OK)
			.body(
				ApiUtils.success(orderItems)
			);
	}
}
