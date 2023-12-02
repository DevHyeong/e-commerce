package org.commerce.order.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductPk implements Serializable {
    private Long orderId;
    private Long productId;

}
