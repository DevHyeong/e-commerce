package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.common.exception.ProductNotFoundException;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.entity.Category;
import org.commerce.product.entity.Product;
import org.commerce.product.entity.ProductCategory;
import org.commerce.product.repository.CategoryRepository;
import org.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.commerce.product.service.SimpleProductFactory.createProductCategories;
import static org.commerce.product.service.SimpleProductFactory.toResponse;

@Service
@RequiredArgsConstructor
public class ProductReadService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponse getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("product id %s is not found", productId)));
        List<Category> categories = categoryRepository.findAllById(product.getCategories().stream()
                .map(e-> e.getCategoryId())
                .collect(Collectors.toList()));
        product.setCategories(createProductCategories(product,  categories.stream()
                .map(e-> new CategoryDto(e.getId(), e.getName()))
                .collect(Collectors.toList())));
        return toResponse(product);
    }

    public List<ProductResponse> getProducts(List<Long> productIds){
        List<Product> products = productRepository.findAllById(productIds);
        List<ProductCategory> categories = categoryService.getProductCategories(productIds);
        return products.stream()
                .map(e-> toResponse(e))
                .collect(Collectors.toList());
    }


    private List<CategoryDto> toEqualsProductIdCategories(Long productId, List<ProductCategory> categories){
        return categories.stream()
                .filter(e-> e.getProductId() == productId)
                .map(e-> new CategoryDto(e.getCategoryId(), e.getCategoryName()))
                .collect(Collectors.toList());
    }


}
