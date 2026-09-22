package com.gowsika.gowsikamart.repository;

import com.gowsika.gowsikamart.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
