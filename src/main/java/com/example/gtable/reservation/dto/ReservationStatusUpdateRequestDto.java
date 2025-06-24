package com.example.gtable.reservation.dto;

import com.example.gtable.reservation.entity.ReservationStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationStatusUpdateRequestDto {
	private ReservationStatus status;
}
