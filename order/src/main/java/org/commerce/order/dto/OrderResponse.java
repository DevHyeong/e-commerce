package org.commerce.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.commerce.order.entity.OrderProduct;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.entity.Orderer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class OrderResponse {
    private Long orderId;
    private OrdererDto orderer;
    private OrderStatus status;
    private List<OrderProductDto> products;
    private LocalDateTime createdAt;

    public OrderResponse(Long orderId,
                         Orderer orderer,
                         OrderStatus status,
                         List<OrderProduct> products,
                         LocalDateTime createdAt){
        this.orderId = orderId;
        this.orderer = new OrdererDto(orderer.getUserId(), orderer.getReceiver());
        this.status = status;
        this.products = products.stream()
                .map(e-> {
                    OrderProductDto p = new OrderProductDto(e.getProductId(), e.getPrice(), e.getAmount());
                    return p;
                })
                .collect(Collectors.toList());
        this.createdAt = createdAt;
    }
}
