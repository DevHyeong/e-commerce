package org.commerce.product.repository;

import lombok.RequiredArgsConstructor;
import org.commerce.product.entity.Category;
import org.commerce.product.entity.ProductCategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository{
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    static final String TABLE_NAME = "category";
    static final String PRODUCT_CATEGORY_TABLE_NAME = "product_category";
    static final RowMapper<Category> rowMapper = (ResultSet rs, int rowNum) ->
            new Category(
                    rs.getLong("id"),
                    rs.getLong("upper_id"),
                    rs.getString("name")
            );

    @Override
    public List<Category> findUpperAllCategoriesById(Long id) {
        String sql = "with recursive rc as (";
        sql += "select id, upper_id, name, 1 as row_num from %s where id = :id";
        sql += " union all";
        sql += " select category.id, category.upper_id, category.name, row_num + 1 as row_num";
        sql += " from category join rc on category.id = rc.upper_id ";
        sql += ") select * from rc order by row_num desc";

        var params = new MapSqlParameterSource().addValue("id", id);
        return namedParameterJdbcTemplate.query(String.format(sql, TABLE_NAME), params, rowMapper);
    }

    @Override
    public List<ProductCategory> findUpperAllCategoriesByIds(Iterable ids) {
        String sql = "SELECT A.product_id, B.id, B.name FROM %s A ";
        sql += "LEFT JOIN %s B ON A.category_id = B.id ";
        sql += "WHERE product_id in ( :ids )";
        sql += "order by A.product_id, A.category_rank";

        var params = new MapSqlParameterSource();
        params.addValue("ids", ids);

        return namedParameterJdbcTemplate.query(
                String.format(sql, TABLE_NAME, PRODUCT_CATEGORY_TABLE_NAME),
                params,
                (rs, rowNum) -> new ProductCategory(
                        rs.getLong("product_id"),
                        rs.getLong("id"),
                        rs.getString("name")
                )
        );
    }
}
