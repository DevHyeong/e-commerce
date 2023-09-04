package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.entity.Category;
import org.commerce.product.entity.Product;
import org.commerce.product.entity.ProductCategory;
import org.commerce.product.repository.ProductCategoryRepository;
import org.commerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.commerce.product.service.SimpleProductFactory.*;

@Service
@RequiredArgsConstructor
public class ProductWriteService {
    private final CategoryService categoryService;
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Category category = categoryService.getCategory(productRequest.getCategoryId());
        if(categoryService.hasLowerCategory(category.getId()))
            throw new IllegalArgumentException("This is not a valid category id for product registration");
        List<CategoryDto> categories = categoryService.getUpperAllCategories(productRequest.getCategoryId());
        Product product = toEntity(productRequest, categories);
        Product result = productRepository.save(product);
        return toResponse(result);
    }
}
