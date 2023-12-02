package org.commerce.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Orderer {
    private Long userId;
    @Embedded
    private Receiver receiver;

    public Orderer(Long userId, Receiver receiver){
        this.userId = userId;
        this.receiver = receiver;
    }
}
