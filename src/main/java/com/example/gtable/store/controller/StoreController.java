package com.example.gtable.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.dto.StoreUpdateRequest;
import com.example.gtable.store.service.StoreService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Tag(name = "Store API", description = "주점 API")
@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@PostMapping
	@Operation(summary = "주점 등록", description = "관리자의 주점 등록")
	@ApiResponse(responseCode = "201", description = "주점 생성/등록")
	public ResponseEntity<?> createStore(@Valid @RequestBody StoreCreateRequest request) {
		StoreCreateResponse response = storeService.createStore(request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}

	@GetMapping("/all-stores")
	@Operation(summary = "모든 주점 리스트 조회", description = "등록된 모든 주점 리스트 조회")
	@ApiResponse(responseCode = "200", description = "전체 주점 리스트 조회")
	public ResponseEntity<?> getAllStores() {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					storeService.getAllStores()
				)
			);
	}

	@GetMapping("/{storeId}")
	@Operation(summary = "특정 주점 조회", description = "특정 주점 정보 개별 조회")
	@ApiResponse(responseCode = "200", description = "주점 조회")
	public ResponseEntity<?> getStoreById(@PathVariable Long storeId) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					storeService.getStoreByStoreId(storeId)
				)
			);
	}

	@PatchMapping("/{storeId}")
	@Operation(summary = "주점 수정", description = "관리자의 주점 정보 수정")
	@ApiResponse(responseCode = "200", description = "주점 수정")
	public ResponseEntity<?> updateStore(
		@PathVariable Long storeId,
		@Valid @RequestBody StoreUpdateRequest request
	) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					storeService.updateStore(storeId, request)
				)
			);
	}

	@DeleteMapping("/{storeId}")
	@Operation(summary = "주점 삭제", description = "관리자의 주점 삭제")
	@ApiResponse(responseCode = "200", description = "주점 삭제")
	public ResponseEntity<?> deleteStore(@PathVariable Long storeId) {
		return ResponseEntity
			.ok()
			.body(
				ApiUtils.success(
					storeService.deleteStore(storeId)
				)
			);
	}

	@GetMapping("/search")
	@Operation(summary = "주점 검색", description = "등록된 주점 중 검색")
	@ApiResponse(responseCode = "200", description = "주점 검색")
	public ResponseEntity<?> searchStores(@RequestParam("name") String name) {
		return ResponseEntity
			.ok()
			.body(
				ApiUtils.success(
					storeService.searchStoresByName(name)
				)
			);
	}
}
