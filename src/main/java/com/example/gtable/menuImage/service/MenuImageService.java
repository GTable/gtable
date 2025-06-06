package com.example.gtable.menuImage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.gtable.global.s3.S3Service;
import com.example.gtable.menu.model.Menu;
import com.example.gtable.menu.repository.MenuRepository;
import com.example.gtable.menuImage.dto.MenuImageUploadResponse;
import com.example.gtable.menuImage.model.MenuImage;
import com.example.gtable.menuImage.repository.MenuImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuImageService {

	private final MenuRepository menuRepository;
	private final MenuImageRepository menuImageRepository;
	private final S3Service s3Service;

	@Transactional
	public MenuImageUploadResponse save(Long menuId, MultipartFile file) {

		String type = "menu";
		Menu menu = menuRepository.findById(menuId)
			.orElseThrow(() -> new IllegalArgumentException("Menu not found with id: " + menuId));

		S3Service.S3UploadResult uploadResult = s3Service.upload(type, menuId, file).join();

		// MenuImage 엔티티 생성 및 저장
		MenuImage menuImage = MenuImage.builder()
			.menu(menu)
			.imageUrl(uploadResult.url())
			.fileKey(uploadResult.key())
			.build();

		menuImageRepository.save(menuImage);

		// 응답 생성
		return MenuImageUploadResponse.fromEntity(menuImage);
	}

	@Transactional
	public void delete(Long id) {
		MenuImage menuImage = menuImageRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("MenuImage not found with id: " + id));

		s3Service.delete(menuImage.getFileKey());
		menuImageRepository.delete(menuImage);
	}
}
