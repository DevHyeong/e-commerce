package org.commerce.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long upperId;
    private String name;
    private LocalDateTime createdAt;

    public Category(Long upperId, String name){
        this.upperId = upperId;
        this.name = name;
    }

    public boolean isTopLevelCatgeory(){
        return upperId == 0L;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
