package com.example.gtable.storeImage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.gtable.global.s3.S3Service;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import com.example.gtable.storeImage.dto.StoreImageUploadResponse;
import com.example.gtable.storeImage.model.StoreImage;
import com.example.gtable.storeImage.repository.StoreImageRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreImageService {

	private final StoreRepository storeRepository;
	private final StoreImageRepository storeImageRepository;
	private final S3Service s3Service;

	@Transactional
	public List<StoreImageUploadResponse> saveAll(Long storeId, List<MultipartFile> files, List<String> types) {
		if (files.size() != types.size()) {
			throw new IllegalArgumentException("파일과 타입의 개수가 일치해야 합니다.");
		}

		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new EntityNotFoundException("Store not found with id: " + storeId));

		// 모든 파일을 비동기로 업로드
		List<CompletableFuture<S3Service.S3UploadResult>> uploadFutures = new ArrayList<>();
		for (MultipartFile file : files) {
			uploadFutures.add(s3Service.upload(storeId, file));
		}

		// 모든 업로드 완료 대기
		List<S3Service.S3UploadResult> uploadResults;
		try {
			uploadResults = uploadFutures.stream()
				.map(CompletableFuture::join)
				.toList();
		} catch (Exception e) {
			throw new RuntimeException("S3 업로드 실패", e);
		}

		// DB 저장은 모든 S3 업로드 성공 후 수행
		List<StoreImageUploadResponse> imageUploadResponses = new ArrayList<>();
		for (int i = 0; i < uploadResults.size(); i++) {
			S3Service.S3UploadResult uploadResult = uploadResults.get(i);
			StoreImage storeImage = StoreImage.builder()
				.store(store)
				.imageUrl(uploadResult.url())
				.fileKey(uploadResult.key())
				.type(types.get(i))
				.build();

			storeImageRepository.save(storeImage);
			imageUploadResponses.add(StoreImageUploadResponse.fromEntity(storeImage));
		}

		return imageUploadResponses;
	}

	@Transactional
	public void delete(Long storeImageId) {
		StoreImage storeImage = storeImageRepository.findById(storeImageId)
			.orElseThrow(() -> new EntityNotFoundException("StoreImage not found with id: " + storeImageId));

		s3Service.delete(storeImage.getFileKey());
		storeImageRepository.delete(storeImage);
	}
}
