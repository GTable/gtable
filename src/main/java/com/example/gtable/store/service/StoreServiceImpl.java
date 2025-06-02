package com.example.gtable.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.dto.StoreReadDto;
import com.example.gtable.store.dto.StoreReadResponse;
import com.example.gtable.store.dto.StoreUpdateRequest;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

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
			.map(StoreReadDto::fromEntity)
			.toList();

		boolean hasNext = false;

		return StoreReadResponse.fromEntity(
			storeRead,
			hasNext
		);
	}

	@Override
	@Transactional(readOnly = true)
	public StoreReadDto getStoreByStoreId(Long storeId) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));

		return StoreReadDto.fromEntity(store);
	}

	@Override
	@Transactional
	public StoreReadDto updateStore(Long storeId, StoreUpdateRequest request) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));
		;

		if (request.getName() != null)
			store.setName(request.getName());
		if (request.getLocation() != null)
			store.setLocation(request.getLocation());
		if (request.getDescription() != null)
			store.setDescription(request.getDescription());
		if (request.getStoreImageUrl() != null)
			store.setStoreImageUrl(request.getStoreImageUrl());
		if (request.getIsActive() != null)
			store.setIsActive(request.getIsActive());

		Store updatedStore = storeRepository.save(store);

		return StoreReadDto.fromEntity(updatedStore);
	}

	@Override
	@Transactional
	public String deleteStore(Long storeId) {
		Store store = storeRepository.findByStoreIdAndDeletedFalse(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));

		store.setDeleted(true);
		storeRepository.save(store);

		return "Store ID " + storeId + " 삭제되었습니다.";
	}
}
