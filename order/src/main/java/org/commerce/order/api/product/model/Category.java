package org.commerce.order.api.product.model;

import lombok.Getter;

@Getter
public class Category {
    private Long id;
    private Long upperId;
    private String name;

    public Category(){

    }

    public Category(Long id, Long upperId, String name){
        this.id = id;
        this.upperId = upperId;
        this.name = name;
    }
}
