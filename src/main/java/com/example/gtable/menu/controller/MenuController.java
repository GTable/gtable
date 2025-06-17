package com.example.gtable.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.menu.dto.MenuCreateRequest;
import com.example.gtable.menu.dto.MenuCreateResponse;
import com.example.gtable.menu.service.MenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Tag(name = "Menu API", description = "메뉴 API")
@RestController
@RequestMapping("/admin/menus")
@RequiredArgsConstructor
public class MenuController {

	private final MenuService menuService;

	@PostMapping("/create")
	@Operation(summary = "메뉴 생성", description = "특정 주점에 대한 메뉴 생성")
	@ApiResponse(responseCode = "201", description = "메뉴 생성")
	public ResponseEntity<?> createMenu(@Valid @RequestBody MenuCreateRequest request) {
		MenuCreateResponse response = menuService.createMenu(request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}

	@GetMapping("/all-menus/stores/{storeId}")
	@Operation(summary = "주점 메뉴 조회", description = "특정 주점에 대한 메뉴 리스트 조회")
	@ApiResponse(responseCode = "200", description = "특정 주점에서 등록한 메뉴 리스트 조회")
	public ResponseEntity<?> getMenusByStoreId(@PathVariable Long storeId) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					menuService.getMenusByStoreId(storeId)
				)
			);
	}
}
