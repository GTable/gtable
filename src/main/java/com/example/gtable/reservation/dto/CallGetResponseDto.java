package com.example.gtable.reservation.dto;

import java.time.LocalDateTime;

import com.example.gtable.reservation.entity.Reservation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CallGetResponseDto {
	private Long id;
	private Long storeId;
	private String userName;
	private LocalDateTime requestedAt;
	private String status;
	private Integer partySize;

	public static CallGetResponseDto fromEntity(Reservation reservation) {
		return CallGetResponseDto.builder()
			.id(reservation.getId())
			.storeId(reservation.getStore().getStoreId())
			.userName(reservation.getUser().getNickname())
			.requestedAt(reservation.getRequestedAt())
			.status(reservation.getStatus().name())
			.partySize(reservation.getPartySize())
			.build();
	}
}
