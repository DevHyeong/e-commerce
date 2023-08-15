package org.commerce.order.dto;

import lombok.Data;

@Data
public class OrderProductDto {
    private Long productId;
    private int price;
    private int amount;


    public OrderProductDto(Long productId, int price, int amount){
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }
}
