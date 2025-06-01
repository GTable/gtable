package com.example.gtable.store.dto;

import com.example.gtable.global.entity.BaseTimeEntity;
import com.example.gtable.store.model.Store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreCreateRequest extends BaseTimeEntity {

	@NotNull
	private Long departmentId;

	@NotBlank
	private String name;

	private String location;

	private String description;

	private String storeImageUrl;

	public Store toEntity() {
		return Store.builder()
			.departmentId(departmentId)
			.name(name)
			.location(location)
			.description(description)
			.storeImageUrl(storeImageUrl)
			.isActive(false)
			.build();
	}
}
