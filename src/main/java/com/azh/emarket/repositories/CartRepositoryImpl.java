package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.BadRequestException;
import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CartRepositoryImpl implements CartRepository {

    private static final String SQL_FIND_CART_CUSTOMER = "SELECT CA.PRODUCT_ID, P.NAME AS PRODUCT_NAME, P.PRICE, CA.PRODUCT_AMOUNT, P.CATEGORY_ID, CT.NAME AS CATEGORY_NAME, " +
            "P.SELLER_ID, S.NAME AS SELLER_NAME " +
            "FROM CART CA JOIN PRODUCT P ON P.ID=CA.PRODUCT_ID " +
            "JOIN CATEGORY CT ON CT.ID=P.CATEGORY_ID JOIN SELLER S ON S.ID=P.SELLER_ID WHERE CA.CUSTOMER_ID=?";

    private static final String SQL_FIND_CART_CUSTOMER_PRODUCT = "SELECT CA.PRODUCT_ID, P.NAME AS PRODUCT_NAME, P.PRICE, CA.PRODUCT_AMOUNT, P.CATEGORY_ID, CT.NAME AS CATEGORY_NAME, " +
            "P.SELLER_ID, S.NAME AS SELLER_NAME " +
            "FROM CART CA JOIN PRODUCT P ON P.ID=CA.PRODUCT_ID " +
            "JOIN CATEGORY CT ON CT.ID=P.CATEGORY_ID JOIN SELLER S ON S.ID=P.SELLER_ID WHERE CA.CUSTOMER_ID=? AND CA.PRODUCT_ID=?";

    private static final String SQL_FIND_CUSTOMER_PRODUCT = "SELECT PRODUCT_AMOUNT FROM CART WHERE CUSTOMER_ID=? AND PRODUCT_ID=?";

    private static final String SQL_ADD_CUSTOMER_PRODUCT = "INSERT INTO CART(CUSTOMER_ID, PRODUCT_ID, PRODUCT_AMOUNT) VALUES(?,?,?)";

    private static final String SQL_UPDATE_CUSTOMER_PRODUCT = "UPDATE CART SET PRODUCT_AMOUNT=? WHERE CUSTOMER_ID=? AND PRODUCT_ID=?";

    private static final String SQL_DELETE_CUSTOMER_PRODUCT = "DELETE FROM CART WHERE CUSTOMER_ID=? AND PRODUCT_ID=?";

    private static final String SQL_DELETE_CUSTOMER_CART = "DELETE FROM CART WHERE CUSTOMER_ID=?";

    private static final String SQL_ADD_TO_CHECKOUT = "INSERT INTO CHECKOUT(CUSTOMER_ID, PRODUCT_ID, PRODUCT_AMOUNT) VALUES(?,?,?)";

    private static final String SQL_FIND_CHECKOUT_CUSTOMER = "SELECT CA.TIMESTAMP, CA.PRODUCT_ID, P.NAME AS PRODUCT_NAME, P.PRICE, CA.PRODUCT_AMOUNT, P.CATEGORY_ID, CT.NAME AS CATEGORY_NAME, " +
            "P.SELLER_ID, S.NAME AS SELLER_NAME " +
            "FROM CHECKOUT CA JOIN PRODUCT P ON P.ID=CA.PRODUCT_ID " +
            "JOIN CATEGORY CT ON CT.ID=P.CATEGORY_ID JOIN SELLER S ON S.ID=P.SELLER_ID WHERE CA.CUSTOMER_ID=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Cart> findCartByCustomer(Integer customerId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_CART_CUSTOMER, new Object[]{customerId}, cartRowMapper);
        }catch (Exception e){
            throw new ResourceNotFoundException("Cart not found");
        }
    }

    @Override
    public Cart findCartByCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_CART_CUSTOMER_PRODUCT, new Object[]{customerId, productId}, cartRowMapper);
        }catch (Exception e){
            throw new ResourceNotFoundException("Cart product not found");
        }
    }

    @Override
    public boolean insertToCart(Integer customerId, Integer productId, Integer productAmount) throws BadRequestException {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_ADD_CUSTOMER_PRODUCT);
                ps.setInt(1, customerId);
                ps.setInt(2, productId);
                ps.setInt(3, productAmount);
                return ps;
            });
            return true;
        }catch (Exception e){
            throw new BadRequestException("Failed to add product to cart");
        }
    }

    @Override
    public boolean updateProductAmount(Integer customerId, Integer productId, Integer productAmount) throws BadRequestException {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_UPDATE_CUSTOMER_PRODUCT);
                ps.setInt(1, productAmount);
                ps.setInt(2, customerId);
                ps.setInt(3, productId);
                return ps;
            });
            return true;
        }catch (Exception e){
            throw new BadRequestException("Failed to update product amount to cart");
        }
    }

    @Override
    public Integer findCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_CUSTOMER_PRODUCT, new Object[]{customerId, productId}, productAmountRowMapper);
        }catch (Exception e){
            throw new ResourceNotFoundException("product not found");
        }
    }

    @Override
    public boolean deleteCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE_CUSTOMER_PRODUCT);
                ps.setInt(1, customerId);
                ps.setInt(2, productId);
                return ps;
            });
            return true;
        }catch (Exception e){
            throw new BadRequestException("Failed to delete product to cart");
        }
    }

    @Override
    public boolean deleteCustomerCart(Integer customerId) throws ResourceNotFoundException {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_DELETE_CUSTOMER_CART);
                ps.setInt(1, customerId);
                return ps;
            });
            return true;
        }catch (Exception e){
            throw new BadRequestException("Failed to delete customer cart");
        }
    }

    @Override
    public boolean insertIntoCheckout(Integer customerId, Integer productId, Integer productAmount) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_ADD_TO_CHECKOUT);
                ps.setInt(1, customerId);
                ps.setInt(2, productId);
                ps.setInt(3, productAmount);
                return ps;
            });
            return true;
        }catch (Exception e){
            throw new BadRequestException("Failed to add product to checkout");
        }
    }

    @Override
    public List<Checkout> findCheckoutByCustomer(Integer customerId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.query(SQL_FIND_CHECKOUT_CUSTOMER, new Object[]{customerId}, checkoutRowMapper);
        }catch (Exception e){
            throw new ResourceNotFoundException("Checkout not found");
        }
    }

    private RowMapper<Integer> productAmountRowMapper = (rs, rowNum) -> {
        return rs.getInt("PRODUCT_AMOUNT");
    };

    private RowMapper<Cart> cartRowMapper = ((rs, rowNum) -> {
        return new Cart(
                new Product(
                        rs.getInt("PRODUCT_ID"),
                        new Seller(rs.getInt("SELLER_ID"), rs.getString("SELLER_NAME")),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("PRICE"),
                        new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"))
                ),
                rs.getInt("PRODUCT_AMOUNT")
        );
    });

    private RowMapper<Checkout> checkoutRowMapper = ((rs, rowNum) -> {
        return new Checkout(
                rs.getTimestamp("TIMESTAMP"),
                new Product(
                        rs.getInt("PRODUCT_ID"),
                        new Seller(rs.getInt("SELLER_ID"), rs.getString("SELLER_NAME")),
                        rs.getString("PRODUCT_NAME"),
                        rs.getInt("PRICE"),
                        new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"))
                ),
                rs.getInt("PRODUCT_AMOUNT")
        );
    });
}
