package com.example.gtable.store.service;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public StoreCreateResponse createStore(StoreCreateRequest request) {
        Store store = request.toEntity();

        storeRepository.save(store);

        return StoreCreateResponse.fromEntity(store);
    }
}
