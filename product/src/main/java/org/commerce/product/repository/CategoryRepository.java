package org.commerce.product.repository;

import org.commerce.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryCustomRepository {
    List<Category> findByUpperId(Long upperId);
}
