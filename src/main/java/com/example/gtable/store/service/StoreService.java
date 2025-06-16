package com.example.gtable.store.service;

import java.util.List;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.dto.StoreReadDto;
import com.example.gtable.store.dto.StoreReadResponse;
import com.example.gtable.store.dto.StoreUpdateRequest;
import com.example.gtable.store.model.Store;

public interface StoreService {

	StoreCreateResponse createStore(StoreCreateRequest request);

	StoreReadResponse getAllStores();

	StoreReadDto getStoreByStoreId(Long storeId);

	StoreReadDto updateStore(Long storeId, StoreUpdateRequest request);

	String deleteStore(Long storeId);

	List<Store> searchStoresByName(String name);

}
