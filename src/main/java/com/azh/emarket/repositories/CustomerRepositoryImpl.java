package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;
import com.azh.emarket.models.CustomerCredentials;
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

    private static final String SQL_CREATE = "INSERT INTO CUSTOMER(NAME, EMAIL) "+
            "VALUES(?, ?)";

    private static final String SQL_CREDENTIALS_CREATE = "INSERT INTO CUSTOMER_CREDENTIALS(EMAIL, PASSWORD) "+
            "VALUES(?, ?)";
    private static final String SQL_FIND_BY_EMAIL = "SELECT ID, NAME, EMAIL FROM " +
            "CUSTOMER WHERE EMAIL = ?";

    private static final String SQL_FIND_CREDENTIALS_BY_EMAIL = "SELECT EMAIL, PASSWORD FROM " +
            "CUSTOMER_CREDENTIALS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT ID, NAME, EMAIL FROM " +
            "CUSTOMER WHERE ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer create(String name, String email) throws AuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, email);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch (Exception e){
            throw new AuthException("Invalid. Failed to create account");
        }
    }

    @Override
    public Boolean findCredentials(String email, String password) throws AuthException {
        try {
            CustomerCredentials credentials = jdbcTemplate.queryForObject(SQL_FIND_CREDENTIALS_BY_EMAIL, new Object[]{email}, credentialsRowMapper);
            if(password.equals(credentials.getPassword())){
                return true;
            }else {
                throw new AuthException("Invalid password");
            }

        }catch (Exception e){
            throw new AuthException("Invalid username");
        }
    }

    @Override
    public String createCredentials(String email, String password) throws AuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_CREDENTIALS_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, password);
                return ps;
            }, keyHolder);
            return (String) keyHolder.getKeys().get("email");
        }catch (Exception e){
            throw new AuthException("Invalid. Failed to create account");
        }
    }

    @Override
    public Customer findByEmail(String email) throws AuthException {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, customerRowMapper);
    }

    @Override
    public Customer findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id}, customerRowMapper);
    }

    private final RowMapper<Customer> customerRowMapper = ( (rs, rowNum) -> {
        return new Customer(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"));
    });

    private final RowMapper<CustomerCredentials> credentialsRowMapper = ( (rs, rowNum) -> {
        return new CustomerCredentials(
                rs.getString("email"),
                rs.getString("password"));
    });
}