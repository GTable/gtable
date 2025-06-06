package com.example.gtable.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.menu.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findAllByStoreId(Long storeId);
}
