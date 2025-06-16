package com.example.gtable.menuImage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.menu.model.Menu;
import com.example.gtable.menuImage.model.MenuImage;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, Long> {
	List<MenuImage> findByMenu(Menu menu);
}
