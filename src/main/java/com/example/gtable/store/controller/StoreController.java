package com.example.gtable.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
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
}
