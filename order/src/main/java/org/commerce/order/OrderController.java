package org.commerce.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.commerce.order.dto.OrderRequest;
import org.commerce.order.dto.OrderResponse;
import org.commerce.order.entity.Order;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.model.OrderResponseV1;
import org.commerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

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

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderResponseV1> getOrder(@PathVariable Long orderId){
        OrderResponseV1 res = orderService.order(orderId);

        return ResponseEntity.status(200)
                .body(res);
    }
}
