package com.example.gtable.menu.model;

import java.time.LocalDateTime;

import com.example.gtable.global.entity.BaseTimeEntity;

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
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Menu extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private Long storeId;
	private String name;
	private String description;
	private String price;

	public Menu(LocalDateTime createdAt, Long id, Long storeId, String name, String description, String price) {
		super(createdAt);
		this.Id = id;
		this.storeId = storeId;
		this.name = name;
		this.description = description;
		this.price = price;
	}
}
