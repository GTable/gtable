package com.example.gtable.store.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.gtable.store.dto.StoreCreateRequest;
import com.example.gtable.store.dto.StoreCreateResponse;
import com.example.gtable.store.model.Store;
import com.example.gtable.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

	@InjectMocks
	private StoreServiceImpl storeService;

	@Mock
	private StoreRepository storeRepository;

	@Test
	@DisplayName("매장 생성 요청이 유효하면 StoreCreateResponse 반환한다")
	void createStore_success() {

		// given
		StoreCreateRequest request = new StoreCreateRequest(
			1L, "Test Store", "Seoul", "Info", "http://img"
		);

		LocalDateTime now = LocalDateTime.now();
		Store initial = request.toEntity();

		Store savedEntity = Store.builder()
			.storeId(10L)
			.departmentId(initial.getDepartmentId())
			.name(initial.getName())
			.location(initial.getLocation())
			.description(initial.getDescription())
			.storeImageUrl(initial.getStoreImageUrl())
			.isActive(true)
			.createdAt(now)
			.build();

		when(storeRepository.save(any(Store.class))).thenReturn(savedEntity);

		// when
		StoreCreateResponse response = storeService.createStore(request);

		// then
		assertThat(response).isNotNull();
		assertThat(response.getStoreId()).isEqualTo(10L);
		assertThat(response.getDepartmentId()).isEqualTo(1L);
		assertThat(response.getName()).isEqualTo("Test Store");
		assertThat(response.getLocation()).isEqualTo("Seoul");
		assertThat(response.getDescription()).isEqualTo("Info");
		assertThat(response.getStoreImageUrl()).isEqualTo("http://img");
		assertThat(response.getIsActive()).isTrue();
		assertThat(response.getCreatedAt()).isEqualTo(now);

		verify(storeRepository, times(1)).save(any(Store.class));
	}

}
