package com.example.gtable.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gtable.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findAllByStore_StoreIdOrderByRequestedAtAsc(Long storeId);
}
