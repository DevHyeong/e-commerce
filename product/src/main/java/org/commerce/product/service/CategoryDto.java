package org.commerce.product.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {

    private Long id;
    private Long upperId;
    private String name;
    private LocalDateTime createdAt;

    public CategoryDto(Long id, Long upperId, String name, LocalDateTime createdAt){
        this.id = id;
        this.upperId = upperId;
        this.name = name;
        this.createdAt = createdAt;
    }

}
