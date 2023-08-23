package org.commerce.product.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRequest {
    private Long upperId;
    private String name;

    public CategoryRequest(Long upperId, String name){
        this.upperId = upperId;
        this.name = name;
    }
}
