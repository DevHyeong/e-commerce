package org.commerce.product.dto;

import lombok.Data;
import org.commerce.product.entity.Product;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private String categoryName;
    private String name;
    private int price;
    private int totalAmount;
    private LocalDateTime createdAt;

}
