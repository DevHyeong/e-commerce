package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.entity.Category;
import org.commerce.product.entity.Product;
import org.commerce.product.entity.ProductCategory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SimpleProductFactory {
    public static Product toEntity(ProductRequest productRequest, List<CategoryDto> categories){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        return new Product(
                categories.stream()
                        .map(e-> {
                            atomicInteger.incrementAndGet();
                            return new ProductCategory(e.getId(), atomicInteger.intValue());
                        })
                        .collect(Collectors.toList()),
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getTotalAmount()
        );
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .categories(product.getCategories()
                        .stream()
                        .map(e-> new CategoryDto(e.getCategoryId(), e.getCategoryName()))
                        .collect(Collectors.toList()))
                .productName(product.getName())
                .price(product.getPrice())
                .totalAmount(product.getTotalAmount())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public static List<ProductCategory> createProductCategories(Product product, List<CategoryDto> categories){
        String errorMessage = "There`s nothing in equals between product category id and category id %s";
        return categories.stream()
                .map(e-> {
                    ProductCategory pc = product.getCategories().stream()
                            .filter(c-> c.getCategoryId().equals(e.getId()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new IllegalArgumentException(String.format(errorMessage, e.getId())));
                    return new ProductCategory(
                            pc.getId(),
                            pc.getProductId(),
                            pc.getCategoryId(),
                            e.getName(),
                            pc.getCategoryRank()
                    );
                })
                .collect(Collectors.toList());
    }

}
