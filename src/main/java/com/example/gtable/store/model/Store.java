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

	@Column
	private Boolean deleted = false;

	public Store(LocalDateTime createdAt, Long storeId, Long departmentId, String name, String location,
		String description, String storeImageUrl, Boolean isActive, Boolean deleted) {
		super(createdAt);
		this.storeId = storeId;
		this.departmentId = departmentId;
		this.name = name;
		this.location = location;
		this.description = description;
		this.storeImageUrl = storeImageUrl;
		this.isActive = isActive;
		this.deleted = deleted;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStoreImageUrl(String url) {
		this.storeImageUrl = url;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
