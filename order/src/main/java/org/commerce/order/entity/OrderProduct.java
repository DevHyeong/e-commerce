package org.commerce.order.entity;

//import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import org.commerce.order.entity.pk.OrderProductPk;

@Entity
@Getter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    private Long productId;
    private int price;
    private int amount;

    public OrderProduct(){

    }

    public OrderProduct(Long productId, int price, int amount){
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }
}
