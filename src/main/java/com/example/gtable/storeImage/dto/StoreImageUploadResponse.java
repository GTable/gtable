package com.example.gtable.storeImage.dto;

import com.example.gtable.storeImage.model.StoreImage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreImageUploadResponse {
	private final Long id;
	private final String imageUrl;
	private final String type;

	public static StoreImageUploadResponse fromEntity(StoreImage storeImage) {
		return StoreImageUploadResponse.builder()
			.id(storeImage.getId())
			.imageUrl(storeImage.getImageUrl())
			.type(storeImage.getType())
			.build();
	}
}
