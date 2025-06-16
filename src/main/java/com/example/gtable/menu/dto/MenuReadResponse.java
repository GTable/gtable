package com.example.gtable.menu.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MenuReadResponse {

	private List<MenuReadDto> menuReadDto;

	public static MenuReadResponse of(List<MenuReadDto> menuReadDto) {
		return MenuReadResponse.builder()
			.menuReadDto(menuReadDto)
			.build();
	}
}
