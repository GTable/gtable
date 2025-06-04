package com.example.gtable.storeImage.service;

import java.util.ArrayList;
import java.util.List;

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

		List<StoreImageUploadResponse> imageUploadResponses = new ArrayList<>();
		for (int i = 0; i < files.size(); i++) {
			S3Service.S3UploadResult uploadResult;
			try {
				uploadResult = s3Service.upload(storeId, files.get(i)).get();
			} catch (Exception e) {
				throw new RuntimeException("S3 업로드 실패", e);
			}

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
