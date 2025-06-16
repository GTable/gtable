package com.example.gtable.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.bookmark.dto.BookmarkCreateResponse;
import com.example.gtable.bookmark.dto.BookmarkGetResponse;
import com.example.gtable.bookmark.entity.Bookmark;
import com.example.gtable.bookmark.repository.BookmarkRepository;
import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import com.example.gtable.user.entity.User;
import com.example.gtable.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	@Transactional
	public BookmarkCreateResponse createBookmark(Long storeId, CustomOAuth2User customOAuth2User) {
		parameterValidation(storeId, customOAuth2User);
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new EntityNotFoundException(storeId + " store not found."));
		User user = userRepository.findById(customOAuth2User.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		if (bookmarkRepository.existsByUserAndStore(user, store)) {
			throw new IllegalArgumentException("already bookmarked");
		}

		Bookmark bookmark = Bookmark.builder()
			.store(store)
			.user(user)
			.build();

		return BookmarkCreateResponse.fromEntity(bookmarkRepository.save(bookmark));
	}

	@Transactional(readOnly = true)
	public List<BookmarkGetResponse> getBookmarks(CustomOAuth2User customOAuth2User) {
		User user = userRepository.findById(customOAuth2User.getUserId())
			.orElseThrow(() -> new EntityNotFoundException("User not found"));
		return bookmarkRepository.findAllByUser(user)
			.stream()
			.map(BookmarkGetResponse::fromEntity)
			.toList();
	}

	@Transactional
	public String deleteBookmark(Long bookmarkId, CustomOAuth2User customOAuth2User) {
		parameterValidation(bookmarkId, customOAuth2User);
		Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(() -> new EntityNotFoundException(bookmarkId + " bookmark not found."));
		if (bookmark.getUser().getId() != customOAuth2User.getUserId()) {
			throw new IllegalArgumentException("you can only delete your own bookmark");
		}
		bookmarkRepository.delete(bookmark);
		return "Bookmark ID " + bookmarkId + " deleted.";
	}

	private static void parameterValidation(Long storeId, CustomOAuth2User customOAuth2User) {
		// 파라미터 유효성 검사
		if (storeId == null || storeId < 0) {
			throw new IllegalArgumentException("storeId must be a positive number");
		}
		if (customOAuth2User == null || customOAuth2User.getUserId() == null) {
			throw new IllegalArgumentException("UserInfo is required");
		}
	}
}
