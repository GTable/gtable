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
		// TODO 관련 정책 확정되면 메서드로 분리 예정
		// 파일 개수 제한 검증
		if (files.isEmpty() || files.size() > 10) {
			throw new IllegalArgumentException("파일은 1개 이상 10개 이하로 업로드해 주세요.");
		}
		// 파일 크기 검증
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
			}
			if (file.getSize() > 10 * 1024 * 1024) { // 10MB 제한
				throw new IllegalArgumentException("파일 크기는 10MB를 초과할 수 없습니다.");
			}
		}

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
