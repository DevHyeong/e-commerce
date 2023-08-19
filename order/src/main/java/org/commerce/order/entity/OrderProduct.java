package org.commerce.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.commerce.order.entity.pk.OrderProductPk;

@Entity
@Getter
@IdClass(OrderProductPk.class)
public class OrderProduct {
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Id
    private Long productId;
    private int price;
    private int amount;

    public OrderProduct(Long productId, int price, int amount){
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }
}
