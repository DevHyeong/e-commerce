package org.commerce.product.application.controller;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.CategoryRequest;
import org.commerce.product.dto.CategoryDto;
import org.commerce.product.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(value = "")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryDto categoryDto = categoryService.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryDto);
    }

    @GetMapping(value = "/{upperId}")
    public ResponseEntity<List<CategoryDto>> getCategories(@PathVariable String upperId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getCategories(Long.valueOf(upperId)));
    }
}
