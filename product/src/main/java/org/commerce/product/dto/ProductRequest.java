package org.commerce.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private Long categoryId;
    private String name;
    private int price;
    private int totalAmount;

    public ProductRequest(Long categoryId, String name, int price, int totalAmount){
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.totalAmount = totalAmount;
    }
}
