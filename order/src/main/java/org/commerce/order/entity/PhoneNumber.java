package org.commerce.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.StringUtils;


@Embeddable
@Getter
public class PhoneNumber {

    @Column(name = "phone_number")
    private String value;

    public PhoneNumber(){}
    public PhoneNumber(String value){
        this.value = value;
    }

    private void validatePhoneNumber(String value){
        if(StringUtils.isEmpty(value))
            throw new IllegalArgumentException("phoneNumber is empty");
        this.value = value;
    }
}
