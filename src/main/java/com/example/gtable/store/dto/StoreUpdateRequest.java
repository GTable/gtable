package com.example.gtable.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreUpdateRequest {
	private String name;
	private String location;
	private String description;
	private String storeImageUrl;
	private Boolean isActive;
	private Boolean deleted;
}
