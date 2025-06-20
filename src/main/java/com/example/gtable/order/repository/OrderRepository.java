package com.example.gtable.order.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.order.entity.UserOrder;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder,Long> {
	boolean existsBySignatureAndCreatedAtAfter(String signature, LocalDateTime createdAt);

	List<UserOrder> findByStore_StoreIdAndTableIdAndSessionId(Long storeId, Long tableId, String sessionId);


}
