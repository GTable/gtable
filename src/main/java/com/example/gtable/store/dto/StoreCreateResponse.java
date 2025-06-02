package com.example.gtable.store.dto;

import java.time.LocalDateTime;

import com.example.gtable.global.entity.BaseTimeEntity;
import com.example.gtable.store.model.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StoreCreateResponse extends BaseTimeEntity {

	private Long storeId;
	private Long departmentId;
	private String name;
	private String location;
	private String description;
	private String storeImageUrl;
	private Boolean isActive;
	private Boolean deleted;
	private LocalDateTime createdAt;

	public static StoreCreateResponse fromEntity(Store store) {
		return StoreCreateResponse.builder()
			.createdAt(store.getCreatedAt())
			.storeId(store.getStoreId())
			.departmentId(store.getDepartmentId())
			.name(store.getName())
			.location(store.getLocation())
			.description(store.getDescription())
			.storeImageUrl(store.getStoreImageUrl())
			.isActive(store.getIsActive())
			.deleted(store.getDeleted())
			.build();
	}
}
