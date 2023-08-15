package org.commerce.order;

import lombok.RequiredArgsConstructor;
import org.commerce.order.dto.OrderRequest;
import org.commerce.order.dto.OrderResponse;
import org.commerce.order.entity.Order;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest orderRequest){
        Order order = new Order(
                orderRequest.toOrderer(),
                OrderStatus.PREPARING_FOR_PRODUCT,
                orderRequest.toOrderProduct()
            );
        Order result = orderService.create(order);
        OrderResponse res = new OrderResponse(
                result.getId(),
                result.getOrderer(),
                result.getStatus(),
                result.getOrderProducts(),
                result.getCreatedAt()
            );
        return ResponseEntity.status(201)
                .body(res);
    }
}
