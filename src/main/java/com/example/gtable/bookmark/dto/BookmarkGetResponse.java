package com.example.gtable.bookmark.dto;

import java.util.Collection;
import java.util.List;

import com.example.gtable.bookmark.entity.Bookmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookmarkGetResponse {
	private Long bookmarkId;
	private Long userId;
	private Long storeId;

	public static BookmarkGetResponse fromEntity(Bookmark bookmark) {
		return BookmarkGetResponse.builder()
			.bookmarkId(bookmark.getId())
			.userId(bookmark.getUser().getId())
			.storeId(bookmark.getStore().getStoreId())
			.build();
	}
}
