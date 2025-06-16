package com.example.gtable.storeImage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.store.model.Store;
import com.example.gtable.storeImage.model.StoreImage;

@Repository
public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

	List<StoreImage> findByStore(Store store);
}
