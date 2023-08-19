package org.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.commerce.order.entity.Order;
import org.commerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public Order create(Order order){

        return orderRepository.save(order);
    }
}
