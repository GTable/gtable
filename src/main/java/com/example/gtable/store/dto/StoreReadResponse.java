package com.example.gtable.store.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StoreReadResponse {

	private List<StoreReadDto> storeReadDtos;
	private boolean hasNext;

	public static StoreReadResponse of(List<StoreReadDto> storeReadDtos, boolean hasNext) {
		return StoreReadResponse.builder()
			.storeReadDtos(storeReadDtos)
			.hasNext(hasNext)
			.build();
	}
}
