package org.commerce.product.repository;

import org.commerce.product.entity.Category;
import org.commerce.product.entity.ProductCategory;

import java.util.List;

public interface CategoryCustomRepository {
    List<Category> findUpperAllCategoriesById(Long id);


}
