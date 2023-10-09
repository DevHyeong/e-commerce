package org.commerce.order.api.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commerce.order.api.product.model.Product;
import org.commerce.order.api.product.service.ProductApiService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Profile("test")
@Slf4j
@Service
@RequiredArgsConstructor
public class MockProductRestTemplate implements ProductApiService {

    private List<Product> products = new ArrayList<>();
    @Override
    public List<Product> getProducts(List<Long> ids) {
        return products.stream().filter(e-> ids.contains(e.getId()))
                        .collect(Collectors.toList());
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }
}
