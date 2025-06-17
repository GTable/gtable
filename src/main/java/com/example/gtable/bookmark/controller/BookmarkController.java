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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "Bookmark API", description = "북마크 API")
@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
	private final BookmarkService bookmarkService;

	@PostMapping("/create/{storeId}")
	@Operation(summary = "북마크 생성", description = "특정 주점에 대한 북마크 생성")
	@ApiResponse(responseCode = "201", description = "북마크 생성")
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
	@Operation(summary = "북마크 조회", description = "내가 북마크한 주점 조회")
	@ApiResponse(responseCode = "200", description = "북마크 조회")
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
	@Operation(summary = "북마크 삭제", description = "특정 주점에 대한 북마크 삭제")
	@ApiResponse(responseCode = "200", description = "북마크 삭제")
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
