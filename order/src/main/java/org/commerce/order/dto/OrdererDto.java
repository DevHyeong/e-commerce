package org.commerce.order.dto;

import lombok.Data;
import org.commerce.order.entity.Receiver;

@Data
public class OrdererDto {
    private Long userId;
    private Receiver receiver;

    public OrdererDto(){

    }

    public OrdererDto(Long userId, Receiver receiver){
        this.userId = userId;
        this.receiver = receiver;
    }
}
