package org.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.dto.CategoryRequest;
import org.commerce.product.entity.Category;
import org.commerce.product.common.exception.CategoryNotFoundException;
import org.commerce.product.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto createCategory(CategoryRequest categoryRequest) {
        Category category = new Category(categoryRequest.getUpperId(), categoryRequest.getName());

        if(!category.isTopLevelCatgeory()){
            getCategory(category.getUpperId());
        }

        Category result = categoryRepository.save(category);
        return toDto(result);
    }

    public List<CategoryDto> getCategories(Long upperId){
        List<Category> categories = categoryRepository.findByUpperId(upperId);
        return categories.stream()
                .map(e-> toDto(e))
                .collect(Collectors.toList());
    }

    public List<CategoryDto> getUpperAllCategories(Long id){
        return categoryRepository.findUpperAllCategoriesById(id)
                .stream()
                .map(e-> toDto(e))
                .collect(Collectors.toList());
    }

    public boolean hasLowerCategory(Long id){
        List<Category> categories = categoryRepository.findByUpperId(id);
        return !categories.isEmpty();
    }

    public Category getCategory(Long id) throws CategoryNotFoundException{
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("category id %s is not found", id)));
    }

    private CategoryDto toDto(Category result) {
        return new CategoryDto(result.getId(), result.getUpperId(), result.getName(), result.getCreatedAt());
    }

}