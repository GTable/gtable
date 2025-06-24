package com.example.gtable.reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gtable.global.security.oauth2.dto.CustomOAuth2User;
import com.example.gtable.reservation.dto.ReservationCreateRequestDto;
import com.example.gtable.reservation.dto.ReservationCreateResponseDto;
import com.example.gtable.reservation.dto.ReservationGetResponseDto;
import com.example.gtable.reservation.entity.Reservation;
import com.example.gtable.reservation.entity.ReservationStatus;
import com.example.gtable.reservation.repository.ReservationRepository;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;
import com.example.gtable.user.entity.User;
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
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 user"));

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
	public List<ReservationGetResponseDto> getReservationListByStoreId(Long storeId) {
		List<Reservation> reservations = reservationRepository.findAllByStore_StoreId(storeId);

		return reservations.stream()
			.map(ReservationGetResponseDto::fromEntity)
			.toList();
	}

	// 나머지 메서드도 동일하게 store/user 접근 시 getId() 활용
}

