package com.example.gtable.reservation.exception;

import com.example.gtable.global.exception.ErrorMessage;

public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException() {
		super(ErrorMessage.NOTFOUND_RESERVATION.getMessage());
	}
}
