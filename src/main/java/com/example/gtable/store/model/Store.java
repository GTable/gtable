package com.example.gtable.store.model;

import java.time.LocalDateTime;

import com.example.gtable.global.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Store extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long storeId;

	private Long departmentId;

	private String name;

	private String location;

	private String description;

	private String storeImageUrl;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = false;

	public Store(LocalDateTime createdAt, Long storeId, Long departmentId, String name, String location,
		String description, String storeImageUrl, Boolean isActive) {
		super(createdAt);
		this.storeId = storeId;
		this.departmentId = departmentId;
		this.name = name;
		this.location = location;
		this.description = description;
		this.storeImageUrl = storeImageUrl;
		this.isActive = isActive;
	}
}
