package com.example.gtable.storeImage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.storeImage.dto.StoreImageUploadResponse;
import com.example.gtable.storeImage.service.StoreImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreImageController {

	private final StoreImageService storeImageService;

	@PostMapping("/{storeId}/images")
	public ResponseEntity<?> uploadStoreImage(
		@PathVariable Long storeId,
		@RequestParam("files") List<MultipartFile> files,
		@RequestParam(value = "types") List<String> types
	) {
		List<StoreImageUploadResponse> response = storeImageService.saveAll(storeId, files, types);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}

	@DeleteMapping("/image/{imageId}")
	public ResponseEntity<?> deleteStoreImage(@PathVariable Long imageId) {
		storeImageService.delete(imageId);
		return ResponseEntity
			.status(HttpStatus.NO_CONTENT)
			.body(
				ApiUtils
					.success(
						"Store image deleted successfully."
					)
			);
	}
}
