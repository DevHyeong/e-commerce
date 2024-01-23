package org.commerce.order.repository;


import lombok.RequiredArgsConstructor;
import org.commerce.order.entity.Order;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Order> orders(){
        String sql = "SELECT ";
        sql += "FROM order A ";
        sql += "JOIN order_product B ON A.id = B.order_id ";
        sql += "WHERE A.user_id = ? ";
        sql += "order by A.id desc limit ?";


        //SqlParameterSource parameterSource = new BeanPropertySqlParameterSource();
        //jdbcTemplate.query(sql, )

        return new ArrayList<>();
    }

}
