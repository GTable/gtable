package com.example.gtable.store.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.gtable.store.model.Store;
import com.example.gtable.storeImage.dto.StoreImageUploadResponse;

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
	private List<StoreImageUploadResponse> images;
	private Boolean isActive;
	private Boolean deleted;
	private LocalDateTime createdAt;

	public static StoreReadDto fromEntity(Store store, List<StoreImageUploadResponse> images) {
		return StoreReadDto.builder()
			.createdAt(store.getCreatedAt())
			.storeId(store.getStoreId())
			.departmentId(store.getDepartmentId())
			.name(store.getName())
			.location(store.getLocation())
			.description(store.getDescription())
			.isActive(store.getIsActive())
			.deleted(store.getDeleted())
			.images(images)
			.build();
	}
}
