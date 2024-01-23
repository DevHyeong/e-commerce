package org.commerce.order;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commerce.order.dto.OrderRequest;
import org.commerce.order.dto.OrderResponse;
import org.commerce.order.entity.Order;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.model.OrderResponseV1;
import org.commerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(value = "/create")
    public ResponseEntity<OrderResponse> create(HttpServletRequest request, @RequestBody OrderRequest orderRequest){
        Order order = new Order(
                Long.parseLong(request.getHeader("X-AUTHORIZATION-ID")),
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
        return ResponseEntity.status(200)
                .body(orderService.order(orderId));
    }

    @GetMapping(value = "/orders/{userId}")
    public void orders(@PathVariable Long userId){

    }
}
