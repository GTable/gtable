package com.example.gtable.orderItem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gtable.orderItem.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
