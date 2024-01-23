package org.commerce.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@NoArgsConstructor
public class Orderer {
    @Setter
    private Long userId;
    @Embedded
    private Receiver receiver;

    public Orderer(Long userId, Receiver receiver){
        this.userId = userId;
        this.receiver = receiver;
    }
}
