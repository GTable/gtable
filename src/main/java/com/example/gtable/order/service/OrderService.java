package com.example.gtable.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.example.gtable.menu.model.Menu;
import com.example.gtable.menu.repository.MenuRepository;
import com.example.gtable.order.dto.CartItemDto;
import com.example.gtable.order.dto.OrderCreateRequestDto;
import com.example.gtable.order.dto.OrderCreateResponseDto;
import com.example.gtable.order.entity.UserOrder;
import com.example.gtable.order.exception.DuplicateOrderException;
import com.example.gtable.order.exception.OrderItemsEmptyException;
import com.example.gtable.order.exception.OrderParameterEmptyException;
import com.example.gtable.order.repository.OrderRepository;
import com.example.gtable.orderitem.dto.OrderItemListGetResponseDto;
import com.example.gtable.orderitem.entity.OrderItem;
import com.example.gtable.orderitem.repository.OrderItemRepository;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;
	private final OrderItemRepository orderItemRepository;
	@Transactional
	public OrderCreateResponseDto createOrder(Long storeId, Long tableId,
		OrderCreateRequestDto orderCreateRequestDto, String sessionId) {
		parameterValidation(storeId, tableId, orderCreateRequestDto);

		// 💡 [중복 주문 방지] signature 생성 및 체크
		String signature = generateOrderSignature(storeId, tableId, orderCreateRequestDto.getItems());
		checkDuplicateOrderSignature(signature);

		// 1. Store 조회
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("store not found"));

		// 2. UserOrder 생성 및 signature 저장
		UserOrder order = UserOrder.builder()
			.tableId(tableId)
			.store(store)
			.signature(signature) // signature 저장
			.sessionId(sessionId) // sessionId 저장
			.build();
		UserOrder savedOrder = orderRepository.save(order);

		// 3. 메뉴 ID 리스트 수집 -> Map으로 캐싱
		List<Long> menuIds = orderCreateRequestDto.getItems().stream()
			.map(CartItemDto::getMenuId)
			.toList();

		List<Menu> menus = menuRepository.findAllById(menuIds);
		Map<Long, Menu> menuMap = menus.stream()
			.collect(Collectors.toMap(Menu::getId, Function.identity()));

		// 4. 각 장바구니 항목에 대해 OrderItem 생성 및 저장
		List<OrderItem> orderItems = orderCreateRequestDto.getItems().stream()
			.map(item -> {
				Menu menu = Optional.ofNullable(menuMap.get(item.getMenuId()))
					.orElseThrow(() -> new IllegalArgumentException("menu not found: " + item.getMenuId()));
				return OrderItem.builder()
					.userOrder(savedOrder)
					.menu(menu)
					.quantity(item.getQuantity())
					.build();
			})
			.collect(Collectors.toList());


		orderItemRepository.saveAll(orderItems);

		// 5. 응답 반환
		return OrderCreateResponseDto.fromEntity(savedOrder);
	}

	@Transactional(readOnly = true)
	public List<OrderItemListGetResponseDto> getOrderItems(Long storeId, Long tableId, String sessionId) {
		// 1. UserOrder 목록 조회 (storeId, tableId, sessionId 기준)
		List<UserOrder> userOrders = orderRepository.findByStore_StoreIdAndTableIdAndSessionId(storeId, tableId, sessionId);

		// 2. OrderItem으로 변환
		return userOrders.stream()
			.flatMap(order -> order.getOrderItems().stream())
			.map(OrderItemListGetResponseDto::fromEntity)
			.toList();
	}


	private static void parameterValidation(Long storeId, Long tableId, OrderCreateRequestDto orderCreateRequestDto) {
		if (storeId == null || tableId == null || orderCreateRequestDto == null) {
			        throw new OrderParameterEmptyException();
		}
		if (orderCreateRequestDto.getItems() == null || orderCreateRequestDto.getItems().isEmpty()) {
			throw new OrderItemsEmptyException();
		}
	}
	private String generateOrderSignature(Long storeId, Long tableId, List<CartItemDto> items) {
		String cartString = items.stream()
			.sorted((a, b) -> a.getMenuId().compareTo(b.getMenuId())) // 메뉴 ID 기준 정렬
			.map(item -> item.getMenuId() + ":" + item.getQuantity())
			.collect(Collectors.joining(","));
		String raw = storeId + "-" + tableId + "-" + cartString;
		return DigestUtils.md5DigestAsHex(raw.getBytes());
	}

	private void checkDuplicateOrderSignature(String signature) {
		// 최근 2초 이내 동일 signature 주문이 있는지 검사
		LocalDateTime threshold = LocalDateTime.now().minusSeconds(2);
		boolean exists = orderRepository.existsBySignatureAndCreatedAtAfter(signature, threshold);
		if (exists) {
			throw new DuplicateOrderException();
		}
	}
}
