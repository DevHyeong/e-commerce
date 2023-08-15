package org.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.commerce.order.entity.Order;
import org.commerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public Order create(Order order){
        return orderRepository.save(order);
    }
}
