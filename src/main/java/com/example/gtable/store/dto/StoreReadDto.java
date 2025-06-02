package com.example.gtable.store.dto;

import java.time.LocalDateTime;

import com.example.gtable.store.model.Store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StoreReadDto {
	private Long storeId;
	private Long departmentId;
	private String name;
	private String location;
	private String description;
	private String storeImageUrl;
	private Boolean isActive;
	private Boolean deleted;
	private LocalDateTime createdAt;

	public static StoreReadDto fromEntity(Store store) {
		return StoreReadDto.builder()
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
