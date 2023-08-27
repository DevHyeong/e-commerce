package org.commerce.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    private Long categoryId;
    @Transient
    private String categoryName;
    private int categoryRank;

    public ProductCategory(Long categoryId, int categoryRank){
        this.categoryId = categoryId;
        this.categoryRank = categoryRank;
    }
    public ProductCategory(Long productId, Long categoryId, int categoryRank){
        this.productId = productId;
        this.categoryId = categoryId;
        this.categoryRank = categoryRank;
    }

    public ProductCategory(Long productId, Long categoryId, String categoryName){
        this.productId = productId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
