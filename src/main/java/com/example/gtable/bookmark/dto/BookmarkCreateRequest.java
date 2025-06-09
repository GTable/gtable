package com.example.gtable.bookmark.dto;

import com.example.gtable.bookmark.entity.Bookmark;
import com.example.gtable.store.model.Store;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkCreateRequest {
	@NotNull
	private Long bookmarkId;
	private Store store;

	public Bookmark toEntity() {
		return Bookmark.builder()
			.id(bookmarkId)
			.store(store)
			.build();
	}
}
