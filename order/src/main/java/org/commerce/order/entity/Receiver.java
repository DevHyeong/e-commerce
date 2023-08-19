package org.commerce.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Embeddable
@Getter
public class Receiver {

    @Column(name = "receiver_name")
    private String name;
    @Embedded
    private Address address;
    private PhoneNumber phoneNumber;

    public Receiver(){}
    public Receiver(String name, Address address, PhoneNumber phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
