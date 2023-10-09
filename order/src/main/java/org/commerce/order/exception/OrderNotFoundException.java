package org.commerce.order.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long orderId){
        super(String.format("주문번호 %s가 존재하지 않습니다.", orderId));
    }
}
