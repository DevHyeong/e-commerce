package org.commerce.order.entity;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
