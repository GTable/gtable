package com.example.gtable.bookmark.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class BookmarkOwnerMismatchException extends RuntimeException {
	public BookmarkOwnerMismatchException() {
		super(ErrorMessage.NOT_OWN_BOOKMARK.getMessage());
	}
}
