package org.commerce.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String addr1;
    private String addr2;
    private String zipCode;

    public Address(){}

    public Address(String addr1, String addr2, String zipCode){
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.zipCode = zipCode;
    }
}
