package com.example.gtable.reservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.reservation.dto.CallGetResponseDto;
import com.example.gtable.reservation.dto.ReservationCreateRequestDto;
import com.example.gtable.reservation.dto.ReservationCreateResponseDto;
import com.example.gtable.reservation.dto.ReservationGetResponseDto;
import com.example.gtable.reservation.dto.ReservationStatusSummaryDto;
import com.example.gtable.reservation.dto.ReservationStatusUpdateRequestDto;
import com.example.gtable.reservation.entity.Reservation;
import com.example.gtable.reservation.entity.ReservationStatus;
import com.example.gtable.reservation.exception.ReservationNotFoundException;
import com.example.gtable.reservation.repository.ReservationRepository;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import com.example.gtable.user.entity.User;
import com.example.gtable.user.exception.UserNotFoundException;
import com.example.gtable.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;

	@Transactional
	public ReservationCreateResponseDto create(Long storeId, CustomOAuth2User customOAuth2User,
		ReservationCreateRequestDto requestDto) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 store"));
		User user = userRepository.findById(customOAuth2User.getUserId())
			.orElseThrow(UserNotFoundException::new);

		Reservation reservation = Reservation.builder()
			.store(store)
			.user(user)
			.requestedAt(LocalDateTime.now())
			.status(ReservationStatus.WAITING)
			.build();

		Reservation saved = reservationRepository.save(reservation);

		return ReservationCreateResponseDto.builder()
			.id(saved.getId())
			.storeId(saved.getStore().getStoreId())
			.userId(saved.getUser().getId())
			.requestedAt(saved.getRequestedAt())
			.status(saved.getStatus().name())
			.partySize(saved.getPartySize())
			.build();
	}
	@Transactional(readOnly = true)
	public ReservationStatusSummaryDto getReservationListByStoreId(Long storeId) {
		List<Reservation> reservations = reservationRepository.findAllByStore_StoreIdOrderByRequestedAtAsc(storeId);

		// 상태별 카운트 집계
		int waitingCount = 0;
		int confirmedCount = 0;
		int cancelledCount = 0;
		int callingCount = 0;
		List<ReservationGetResponseDto> reservationDtoList = new ArrayList<>();
		for (Reservation r : reservations) {
			if (r.getStatus() == ReservationStatus.WAITING) waitingCount++;
			if (r.getStatus() == ReservationStatus.CONFIRMED) confirmedCount++;
			if (r.getStatus() == ReservationStatus.CANCELLED) cancelledCount++;
			if (r.getStatus() == ReservationStatus.CALLING) callingCount++;
			reservationDtoList.add(ReservationGetResponseDto.fromEntity(r));
		}

		return ReservationStatusSummaryDto.builder()
			.waitingCount(waitingCount)
			.confirmedCount(confirmedCount)
			.cancelledCount(cancelledCount)
			.callingCount(callingCount)
			.reservationList(reservationDtoList)
			.build();
	}
	@Transactional
	public CallGetResponseDto updateReservationStatus(Long reservationId, ReservationStatusUpdateRequestDto requestDto) {
		Reservation reservation = reservationRepository.findById(reservationId)
			.orElseThrow(ReservationNotFoundException::new);
			reservation.updateStatus(requestDto.getStatus());
		return CallGetResponseDto.fromEntity(reservation);
	}


}

