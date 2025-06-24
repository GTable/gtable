package com.example.gtable.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gtable.global.api.ApiUtils;
import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.reservation.dto.CallGetResponseDto;
import com.example.gtable.reservation.dto.ReservationCreateRequestDto;
import com.example.gtable.reservation.dto.ReservationCreateResponseDto;
import com.example.gtable.reservation.dto.ReservationStatusSummaryDto;
import com.example.gtable.reservation.dto.ReservationStatusUpdateRequestDto;
import com.example.gtable.reservation.service.ReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
@Tag(name = "Reservation API", description = "예약 API")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

	private final ReservationService reservationService;

	@PostMapping("/create/{storeId}")
	@Operation(summary = "예약 생성", description = "특정 주점에 대한 예약하기 생성")
	@ApiResponse(responseCode = "201", description = "예약 생성")
	public ResponseEntity<?> create(
		@PathVariable Long storeId,
		@AuthenticationPrincipal CustomOAuth2User customOAuth2User,
		@RequestBody ReservationCreateRequestDto requestDto) {
		ReservationCreateResponseDto response = reservationService.create(storeId, customOAuth2User, requestDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(
				ApiUtils.success(
					response
				)
			);
	}

	@GetMapping("/admin/{storeId}")
	@Operation(summary = "주점별 예약리스트 조회", description = "특정 주점에 대한 예약리스트 조회")
	@ApiResponse(responseCode = "200", description = "예약리스트 조회")
	public ResponseEntity<?> getReservationListByStoreId(@PathVariable Long storeId) {
		ReservationStatusSummaryDto response = reservationService.getReservationListByStoreId(storeId);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					response
				)
			);
	}

	@PatchMapping("/admin/updates/{reservationId}")
	@Operation(summary = "예약팀 상태 변경", description = "특정 예약에 대한 상태 변경(예약중->호출중,호출중->입장완료,취소)")
	@ApiResponse(responseCode = "200", description = "예약팀 상태 변경")
	public ResponseEntity<?> updateReservationStatus(@PathVariable Long reservationId,@RequestBody
		ReservationStatusUpdateRequestDto requestDto) {
		CallGetResponseDto response = reservationService.updateReservationStatus(reservationId,requestDto);
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(
				ApiUtils.success(
					response
				)
			);
	}


}
