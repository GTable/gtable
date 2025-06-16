package com.example.gtable.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.store.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	List<Store> findAllByDeletedFalse();

	Optional<Store> findByStoreIdAndDeletedFalse(Long storeId);

	List<Store> findByNameContainingIgnoreCase(String name);
}
