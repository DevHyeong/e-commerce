package org.commerce.order.api.product.model;

import lombok.Getter;
import org.commerce.order.api.product.model.Category;

import java.util.List;

@Getter
public class Product {
    private Long id;
    private String name;
    private int price;
    private int amount;
    private List<Category> categories;

    public Product(){

    }

    public Product(Long id, String name, List<Category> categories){
        this.id = id;
        this.name = name;
        this.categories = categories;
    }

    public Product(Long id, String name, int price, int amount, List<Category> categories){
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.categories = categories;
    }
}
