package org.commerce.product.repository;

import org.commerce.product.entity.Category;

import java.util.List;

public interface CategoryCustomRepository {
    //List<Category> findUpperAllCategoriesById(Long id);

    List<Category> findUpperAllCategoriesById(Long id);
}
