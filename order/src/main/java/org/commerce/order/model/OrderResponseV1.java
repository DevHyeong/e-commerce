package org.commerce.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.commerce.order.entity.Order;
import org.commerce.order.entity.OrderProduct;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.api.product.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponseV1 {

    private Long orderId;
    private OrderStatus status;
    private Orderer orderer;
    private Receiver receiver;
    private List<Product> products = new ArrayList<>();

    public OrderResponseV1(Order order, String ordererName, List<Product> products){
        this.orderId = order.getId();
        this.status = order.getStatus();
        this.orderer = new Orderer(ordererName);

        org.commerce.order.entity.Address address = order.getOrderer()
                .getReceiver()
                .getAddress();
        Address address1 = new Address(address.getAddr1(), address.getAddr2(), address.getZipCode());
        this.receiver = new Receiver(order
                .getOrderer()
                .getReceiver()
                .getName(),
                address1,
                order.getOrderer().getReceiver().getPhoneNumber().getValue());

        this.products = products.stream()
                .map(e-> {
                    OrderProduct orderProduct = order.getOrderProducts().stream()
                            .filter(o-> o.getProductId().equals(e.getId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(""));

                    return new Product(e.getId(),
                            e.getName(),
                            orderProduct.getPrice(),
                            orderProduct.getAmount(),
                            e.getCategories());
                })
                .collect(Collectors.toList());

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Orderer {
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Receiver {
        private String name;
        private Address address;
        private String phoneNumber;


    }
    @Getter
    @AllArgsConstructor
    public static class Address {
        private String addr1;
        private String addr2;
        private String zipCode;
    }

}
