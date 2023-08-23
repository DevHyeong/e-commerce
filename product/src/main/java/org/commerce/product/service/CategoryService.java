package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.CategoryRequest;
import org.commerce.product.entity.Category;
import org.commerce.product.common.exception.CategoryNotFoundException;
import org.commerce.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest.getUpperId(), categoryRequest.getName());

        if(!category.isTopLevelCatgeory()){
            categoryRepository.findById(category.getUpperId())
                    .orElseThrow(() -> new CategoryNotFoundException("category is not found"));
        }

        Category result = categoryRepository.save(category);
        return toDto(result);
    }

    private CategoryDto toDto(Category result) {
        return new CategoryDto(result.getId(), result.getUpperId(), result.getName(), result.getCreatedAt());
    }

}