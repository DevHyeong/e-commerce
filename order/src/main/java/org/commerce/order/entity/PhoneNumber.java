package org.commerce.order.entity;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class PhoneNumber {
    private String value;

    public PhoneNumber(String value){
        this.value = value;
    }

    private void validatePhoneNumber(String value){
        if(StringUtils.isEmpty(value))
            throw new IllegalArgumentException("phoneNumber is empty");
        this.value = value;
    }
}
