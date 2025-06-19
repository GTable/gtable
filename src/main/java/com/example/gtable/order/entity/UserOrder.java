package com.example.gtable.order.entity;

import com.example.gtable.global.entity.BaseTimeEntity;
import com.example.gtable.menu.model.Menu;
import com.example.gtable.store.model.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class UserOrder extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long tableId;

	// Store와 연관관계 (N:1)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

}
