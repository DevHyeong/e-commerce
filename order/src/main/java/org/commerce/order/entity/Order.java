package org.commerce.order.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Orderer orderer;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private int totalAmount;
    @OneToMany
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private LocalDateTime createdAt;

    public Order(Orderer orderer, OrderStatus status, List<OrderProduct> orderProducts) {
        setOrderProducts(orderProducts);
        setTotalAmount(orderProducts);
        this.orderer = orderer;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    private void setOrderProducts(List<OrderProduct> orderProducts){
        if(orderProducts == null || orderProducts.size() < 1)
            throw new IllegalArgumentException("orderProducts are empty");
        this.orderProducts = orderProducts;
    }

    private void setTotalAmount(List<OrderProduct> orderProducts){
        this.totalAmount = orderProducts.stream()
                .mapToInt((e)-> e.getAmount() * e.getPrice())
                .sum();
    }

}
