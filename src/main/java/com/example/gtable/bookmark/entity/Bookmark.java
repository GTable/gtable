package com.example.gtable.bookmark.entity;

import com.example.gtable.global.entity.BaseTimeEntity;
import com.example.gtable.store.model.Store;
import com.example.gtable.user.entity.User;

import jakarta.persistence.*;
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
@Table(
	name = "bookmark",
	uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "store_id"})
)
public class Bookmark extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// User와 연관관계 (N:1)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id")
	private User user;

	// Store와 연관관계 (N:1)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "store_id")
	private Store store;

}
