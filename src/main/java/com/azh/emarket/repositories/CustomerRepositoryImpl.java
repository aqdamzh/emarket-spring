package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private static final String SQL_CREATE = "INSERT INTO CUSTOMER(NAME, EMAIL, PASSWORD) "+
            "VALUES(?, ?, ?)";
    private static final String SQL_FIND_BY_EMAIL = "SELECT ID, NAME, EMAIL, PASSWORD FROM " +
            "CUSTOMER WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT ID, NAME, EMAIL, PASSWORD FROM " +
            "CUSTOMER WHERE ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String name, String email, String password) throws AuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch (Exception e){
            throw new AuthException("Invalid. Failed to create account");
        }
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) throws AuthException {
        try {
            Customer customer = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, customerRowMapper);
            if(!password.equals(customer.getPassword())){
                throw new AuthException("Invalid password");
            }
            return customer;
        }catch (Exception e){
            throw new AuthException("Invalid username");
        }
    }

    @Override
    public Customer findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, customerRowMapper);
    }

    private final RowMapper<Customer> customerRowMapper = ( (rs, rowNum) -> {
        return new Customer(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"));
    });
}