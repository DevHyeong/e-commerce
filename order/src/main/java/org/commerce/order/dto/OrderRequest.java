package org.commerce.order.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.commerce.order.entity.OrderProduct;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.entity.Orderer;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class OrderRequest {
    private OrdererDto orderer;
    private OrderStatus status;
    private List<OrderProductDto> products;

    public Orderer toOrderer(){
        Orderer o = new Orderer(orderer.getUserId(), orderer.getReceiver());
        return o;
    }

    public List<OrderProduct> toOrderProduct(){
        return products.stream()
                .map(e-> {
                    OrderProduct p = new OrderProduct(e.getProductId(), e.getPrice(), e.getAmount());
                    return p;
                })
                .collect(Collectors.toList());
    }
}
