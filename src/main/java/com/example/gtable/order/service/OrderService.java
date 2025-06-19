package com.example.gtable.order.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.gtable.menu.model.Menu;
import com.example.gtable.menu.repository.MenuRepository;
import com.example.gtable.order.dto.CartItemDto;
import com.example.gtable.order.dto.OrderCreateRequestDto;
import com.example.gtable.order.dto.OrderCreateResponseDto;
import com.example.gtable.order.entity.UserOrder;
import com.example.gtable.order.repository.OrderRepository;
import com.example.gtable.orderItem.entity.OrderItem;
import com.example.gtable.orderItem.repository.OrderItemRepository;
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
	public OrderCreateResponseDto createOrder(Long storeId, Long tableId, OrderCreateRequestDto orderCreateRequestDto) {
		// 1. Store 조회
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("store not found"));

		// 2. UserOrder 생성 및 저장
		UserOrder order = UserOrder.builder()
			.tableId(tableId)
			.store(store)
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
		for (CartItemDto item : orderCreateRequestDto.getItems()) {
			Menu menu = Optional.ofNullable(menuMap.get(item.getMenuId()))
				.orElseThrow(() -> new IllegalArgumentException("menu not found: " + item.getMenuId()));

			OrderItem orderItem = OrderItem.builder()
				.userOrder(savedOrder)
				.menu(menu)
				.quantity(item.getQuantity())
				.build();
			orderItemRepository.save(orderItem);
		}

		// 5. 응답 반환
		return OrderCreateResponseDto.fromEntity(savedOrder);
	}
}
