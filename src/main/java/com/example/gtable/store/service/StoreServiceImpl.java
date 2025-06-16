package com.example.gtable.store.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.dto.StoreReadDto;
import com.example.gtable.store.dto.StoreReadResponse;
import com.example.gtable.store.dto.StoreUpdateRequest;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import com.example.gtable.storeImage.dto.StoreImageUploadResponse;
import com.example.gtable.storeImage.model.StoreImage;
import com.example.gtable.storeImage.repository.StoreImageRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final StoreImageRepository storeImageRepository;

	@Override
	@Transactional
	public StoreCreateResponse createStore(StoreCreateRequest request) {
		Store toSave = request.toEntity();

		Store saved = storeRepository.save(toSave);

		return StoreCreateResponse.fromEntity(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public StoreReadResponse getAllStores() {
		List<Store> stores = storeRepository.findAllByDeletedFalse();

		List<StoreReadDto> storeRead = stores.stream()
			.map(store -> {
				List<StoreImage> images = storeImageRepository.findByStore(store);
				List<StoreImageUploadResponse> imageDto = images.stream()
					.map(StoreImageUploadResponse::fromEntity)
					.toList();
				return StoreReadDto.fromEntity(store, imageDto);
			})
			.toList();

		boolean hasNext = false;

		return StoreReadResponse.of(storeRead, hasNext);
	}

	@Override
	@Transactional(readOnly = true)
	public StoreReadDto getStoreByStoreId(Long storeId) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));

		List<StoreImage> images = storeImageRepository.findByStore(store);
		List<StoreImageUploadResponse> imageDto = images.stream()
			.map(StoreImageUploadResponse::fromEntity)
			.toList();

		return StoreReadDto.fromEntity(store, imageDto);
	}

	@Override
	@Transactional
	public StoreReadDto updateStore(Long storeId, StoreUpdateRequest request) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));

		store.updateInfo(
			request.getName(),
			request.getLocation(),
			request.getDescription()
		);

		Store updatedStore = storeRepository.save(store);

		List<StoreImage> images = storeImageRepository.findByStore(updatedStore);
		List<StoreImageUploadResponse> imageDto = images.stream()
			.map(StoreImageUploadResponse::fromEntity)
			.toList();

		return StoreReadDto.fromEntity(updatedStore, imageDto);
	}

	@Override
	@Transactional
	public String deleteStore(Long storeId) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));

		store.markAsDeleted();
		storeRepository.save(store);

		return "Store ID " + storeId + " 삭제되었습니다.";
	}

	@Override
	public List<StoreReadDto> searchStoresByName(String name) {
		List<Store> stores = storeRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name);
		return stores.stream()
			.map(store -> {
				List<StoreImage> images = storeImageRepository.findByStore(store);
				List<StoreImageUploadResponse> imageDto = images.stream()
					.map(StoreImageUploadResponse::fromEntity)
					.toList();
				return StoreReadDto.fromEntity(store, imageDto);
			})
			.toList();
	}

}
