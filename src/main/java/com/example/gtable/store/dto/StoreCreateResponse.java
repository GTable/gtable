package com.example.gtable.store.dto;

import com.example.gtable.store.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class StoreCreateResponse {

    private Long storeId;
    private Long departmentId;
    private String name;
    private String location;
    private String description;
    private String storeImageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;


    public static StoreCreateResponse fromEntity(Store store) {
        return StoreCreateResponse.builder()
                .storeId(store.getStoreId())
                .departmentId(store.getDepartmentId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .storeImageUrl(store.getStoreImageUrl())
                .isActive(store.getIsActive())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
