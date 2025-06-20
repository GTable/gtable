package com.example.gtable.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.menu.dto.MenuCreateRequest;
import com.example.gtable.menu.dto.MenuCreateResponse;
import com.example.gtable.menu.dto.MenuReadDto;
import com.example.gtable.menu.dto.MenuReadResponse;
import com.example.gtable.menu.model.Menu;
import com.example.gtable.menu.repository.MenuRepository;
import com.example.gtable.menuImage.dto.MenuImageUploadResponse;
import com.example.gtable.menuImage.model.MenuImage;
import com.example.gtable.menuImage.repository.MenuImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final MenuRepository menuRepository;
	private final MenuImageRepository menuImageRepository;

	@Transactional
	public MenuCreateResponse createMenu(MenuCreateRequest request) {
		Menu toSave = request.toEntity();

		Menu saved = menuRepository.save(toSave);

		return MenuCreateResponse.fromEntity(saved);
	}

	@Transactional(readOnly = true)
	public MenuReadResponse getMenusByStoreId(Long storeId) {
		List<Menu> menus = menuRepository.findAllByStoreId(storeId);

		List<MenuReadDto> menuReadResponse = menus.stream()
			.map(menu -> {
				List<MenuImage> images = menuImageRepository.findByMenu(menu);
				List<MenuImageUploadResponse> imageDto = images.stream()
					.map(MenuImageUploadResponse::fromEntity)
					.toList();
				return MenuReadDto.fromEntity(menu, imageDto);
			})
			.toList();

		return MenuReadResponse.of(menuReadResponse);
	}
	@Transactional(readOnly = true)
	public MenuReadDto getMenusByMenuId(Long menuId) {
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new IllegalArgumentException("해당 주점에 해당 메뉴가 존재하지 않습니다."));

		return MenuReadDto.builder()
			.menuId(menuId)
			.storeId(menu.getStoreId())
			.name(menu.getName())
			.description(menu.getDescription())
			.price(menu.getPrice())
			.build();
	}
}
