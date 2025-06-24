package com.example.gtable.bookmark.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class DuplicateBookmarkException extends RuntimeException {
	public DuplicateBookmarkException() {
		super(ErrorMessage.DUPLICATE_BOOKMARK.getMessage());
	}
}
