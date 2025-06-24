package com.example.gtable.reservation.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCreateRequestDto {
	private Integer partySize;
}
