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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@PostMapping
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
