package org.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.commerce.order.api.product.model.Product;
import org.commerce.order.api.product.service.ProductApiService;
import org.commerce.order.api.user.model.User;
import org.commerce.order.api.user.service.UserApiService;
import org.commerce.order.entity.Order;
import org.commerce.order.exception.AuthenticationException;
import org.commerce.order.exception.OrderNotFoundException;
import org.commerce.order.model.OrderResponseV1;
import org.commerce.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductApiService productApiService;
    private final UserApiService userApiService;

    public Order create(Order order){
        User user = userApiService.getUser(order.getOrderer().getUserId());
        if(user == null)
            throw new AuthenticationException("인증이 필요합니다.");
        return orderRepository.save(order);
    }

    public OrderResponseV1 order(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        User user = userApiService.getUser(order.getOrderer().getUserId());
        List<Product> products = productApiService.getProducts(order.getOrderProducts()
                .stream()
                .map(e-> e.getProductId())
                .collect(Collectors.toList())
        );

        return new OrderResponseV1(order, user.getNickname(), products);
    }

    public void orders(Long userId){
        List<Order> orders = orderRepository.findByOrdererUserId(userId);
        if(orders.size() < 1)
            throw new OrderNotFoundException("주문묵록이 존재하지 않습니다.");




    }

}
