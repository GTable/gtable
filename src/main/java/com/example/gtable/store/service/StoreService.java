package com.example.gtable.store.service;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;

public interface StoreService {

    StoreCreateResponse createStore(StoreCreateRequest request);
}
