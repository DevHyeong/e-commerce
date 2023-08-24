package org.commerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long categoryId;
    private String name;
    private int price;
    private int totalAmount;
    private LocalDateTime createdAt;

    public Product(Long categoryId, String name, int price, int totalAmount){
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
