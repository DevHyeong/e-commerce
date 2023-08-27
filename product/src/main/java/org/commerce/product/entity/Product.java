package org.commerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private List<ProductCategory> categories;
    private String name;
    private int price;
    private int totalAmount;
    private LocalDateTime createdAt;

    public Product(List<ProductCategory> categories, String name, int price, int totalAmount){
        this.categories = categories;
        this.name = name;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public void setCategories(List<ProductCategory> categories){
        this.categories = categories;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
