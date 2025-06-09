package com.example.gtable.bookmark.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.bookmark.dto.BookmarkCreateResponse;
import com.example.gtable.bookmark.service.BookmarkService;
import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
	private final BookmarkService bookmarkService;
	@PostMapping("/{storeId}")
	public ResponseEntity<?> createBookmark(@PathVariable Long storeId,@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		BookmarkCreateResponse response = bookmarkService.createBookmark(storeId,customOAuth2User);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}
	@GetMapping
	public ResponseEntity<?> getAllBookmarks(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		return ResponseEntity
			.ok()
			.body(
				ApiUtils.success(
					bookmarkService.getBookmarks(customOAuth2User)
				)
			);
	}
	@DeleteMapping("/{bookmarkId}")
	public ResponseEntity<?> deleteBookmark(@PathVariable Long bookmarkId, @AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		return ResponseEntity
			.ok()
			.body(
				ApiUtils.success(
					bookmarkService.deleteBookmark(bookmarkId,customOAuth2User)
				)
			);
	}
}
