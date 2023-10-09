package org.commerce.order.api.product.service;

import org.commerce.order.api.product.model.Product;

import java.util.List;

public interface ProductApiService {
    List<Product> getProducts(List<Long> ids);
}