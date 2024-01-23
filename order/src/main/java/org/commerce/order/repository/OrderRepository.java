package org.commerce.order.repository;

import org.commerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrdererUserId(Long userId);
}
