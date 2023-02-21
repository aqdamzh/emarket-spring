package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Category;
import com.azh.emarket.models.Product;
import com.azh.emarket.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_ALL = "SELECT P.ID, P.NAME, P.PRICE, P.CATEGORY_ID, C.NAME AS CATEGORY_NAME, P.SELLER_ID, S.NAME AS SELLER_NAME "+
            "FROM PRODUCT P JOIN CATEGORY C ON P.CATEGORY_ID=C.ID JOIN SELLER S ON P.SELLER_ID=S.ID";

    private static final String SQL_FIND_BY_CATEGORY = "SELECT P.ID, P.NAME, P.PRICE, P.CATEGORY_ID, C.NAME AS CATEGORY_NAME, P.SELLER_ID, S.NAME AS SELLER_NAME "+
            "FROM PRODUCT P JOIN CATEGORY C ON P.CATEGORY_ID=C.ID JOIN SELLER S ON P.SELLER_ID=S.ID WHERE C.NAME=?";

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, productRowMapper);
    }

    @Override
    public List<Product> findByCategory(String category) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_BY_CATEGORY, new Object[]{category.toLowerCase()}, productRowMapper);
        }catch (Exception e) {
            throw new ResourceNotFoundException("Products not found");
        }
    }

    private RowMapper<Product> productRowMapper = (((rs, rowNum) -> {
        return new Product(
                rs.getInt("ID"),
                new Seller(rs.getInt("SELLER_ID"), rs.getString("SELLER_NAME")),
                rs.getString("NAME"),
                rs.getInt("PRICE"),
                new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"))
        );
    }));
}
