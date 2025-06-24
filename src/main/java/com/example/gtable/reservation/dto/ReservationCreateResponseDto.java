package com.example.gtable.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationCreateResponseDto {
	private Long id;
	private Long storeId;
	private Long userId;
	private LocalDateTime requestedAt;
	private String status;
	private Integer partySize;
}
