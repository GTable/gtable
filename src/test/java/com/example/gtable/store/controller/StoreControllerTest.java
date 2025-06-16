// package com.example.gtable.store.controller;
//
// import static org.mockito.ArgumentMatchers.*;
// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
// import java.time.LocalDateTime;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import com.example.gtable.store.dto.StoreCreateRequest;
// import com.example.gtable.store.dto.StoreCreateResponse;
// import com.example.gtable.store.service.StoreService;
// import com.fasterxml.jackson.databind.ObjectMapper;
//
// class StoreControllerTest {
//
// 	private MockMvc mockMvc;
//
// 	@Mock
// 	private StoreService storeService;
//
// 	@InjectMocks
// 	private StoreController storeController;
//
// 	private ObjectMapper objectMapper;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
// 		mockMvc = MockMvcBuilders.standaloneSetup(storeController).build();
// 		objectMapper = new ObjectMapper();
// 	}
//
// 	// @Test
// 	// @DisplayName("매장 생성 요청이 유효하면 201Created 반환")
// 	// void success_return_201_Created() throws Exception {
// 	// 	// given
// 	// 	StoreCreateRequest request = new StoreCreateRequest(1L, "Test Store", "Seoul", "Desc", "http://img");
// 	// 	LocalDateTime now = LocalDateTime.now();
// 	// 	StoreCreateResponse responseDto = StoreCreateResponse.builder()
// 	// 		.storeId(10L)
// 	// 		.departmentId(1L)
// 	// 		.name("Test Store")
// 	// 		.location("Seoul")
// 	// 		.description("Desc")
// 	// 		.storeImageUrl("http://img")
// 	// 		.isActive(true)
// 	// 		.createdAt(now)
// 	// 		.build();
// 	//
// 	// 	when(storeService.createStore(any(StoreCreateRequest.class))).thenReturn(responseDto);
// 	//
// 	// 	// when
// 	// 	ResultActions result = mockMvc.perform(
// 	// 		post("/stores").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)));
// 	//
// 	// 	// then
// 	// 	result.andExpect(status().isCreated());
// 	// }
//
// 	@Test
// 	@DisplayName("필수 필드 누락 시 400 Bad Request 반환")
// 	void success_return_400_Bad() throws Exception {
// 		// given
// 		String json = "{\"name\":\"Test Store\",\"location\":\"Seoul\",\"description\":\"Desc\",\"storeImageUrl\":\"http://img\"}";
//
// 		// when
// 		ResultActions result = mockMvc.perform(post("/stores").contentType(MediaType.APPLICATION_JSON).content(json));
//
// 		// then
// 		result.andExpect(status().isBadRequest());
// 	}
// }
