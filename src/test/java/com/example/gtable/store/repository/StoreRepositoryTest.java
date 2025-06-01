package com.example.gtable.store.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.gtable.store.model.Store;

@DataJpaTest
public class StoreRepositoryTest {

	@Autowired
	private StoreRepository storeRepository;

	@Test
	@DisplayName("매장 저장 및 조회")
	void save_and_find() {
		// given
		Store store = Store.builder()
			.departmentId(1L)
			.name("Test Store")
			.location("Seoul")
			.description("설명")
			.storeImageUrl("http://img")
			.isActive(true)
			.createdAt(LocalDateTime.now())
			.build();

		// when
		Store saved = storeRepository.save(store);
		Optional<Store> found = storeRepository.findById(saved.getStoreId());

		// then
		assertThat(found).isPresent();
		assertThat(found.get().getDepartmentId()).isEqualTo(1L);
		assertThat(found.get().getName()).isEqualTo("Test Store");
		assertThat(found.get().getLocation()).isEqualTo("Seoul");
		assertThat(found.get().getDescription()).isEqualTo("설명");
		assertThat(found.get().getStoreImageUrl()).isEqualTo("http://img");
		assertThat(found.get().getIsActive()).isTrue();
	}
}
