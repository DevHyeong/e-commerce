package org.commerce.product.repository;

import lombok.RequiredArgsConstructor;
import org.commerce.product.entity.Category;
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
}
