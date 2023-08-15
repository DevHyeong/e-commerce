package org.commerce.order.entity;

public enum OrderStatus {
    PREPARING_FOR_PRODUCT, PREPARING_FOR_DELIVERY, DELIVERING, DELIVERED, RETURN; // 상품준비중, 배송준비중, 배송중, 배송완료, 반품
}
