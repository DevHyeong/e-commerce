package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.common.exception.ProductNotFoundException;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.entity.Category;
import org.commerce.product.entity.Product;
import org.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Category category = categoryService.getCategory(productRequest.getCategoryId());
        if(categoryService.hasLowerCategory(category.getId()))
            throw new IllegalArgumentException("This is not a valid category id for product registration");

        Product product = new Product(
                productRequest.getCategoryId(),
                productRequest.getName(),
                productRequest.getPrice(),
                productRequest.getTotalAmount()
        );
        Product result = productRepository.save(product);
        List<CategoryDto> categories = categoryService.getUpperAllCategories(result.getCategoryId());
        return toResponse(result, categories);
    }

    public ProductResponse getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("product id %s is not found", productId)));
        List<CategoryDto> categories = categoryService.getUpperAllCategories(product.getCategoryId());
        return toResponse(product, categories);
    }

    private ProductResponse toResponse(Product product, List<CategoryDto> categories) {
        return ProductResponse.builder()
                .categories(categories)
                .productName(product.getName())
                .price(product.getPrice())
                .totalAmount(product.getTotalAmount())
                .createdAt(product.getCreatedAt())
                .build();
    }


}
