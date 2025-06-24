package com.example.gtable.reservation.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.example.gtable.store.model.Store;
import com.example.gtable.user.entity.User;

@Entity
@Table(name = "waiting_requests")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "requested_at", nullable = false)
	private LocalDateTime requestedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReservationStatus status;

	@Column(name = "party_size", nullable = false)
	private Integer partySize;

	public void updateStatus(ReservationStatus status) {
		this.status = status;
	}

}
