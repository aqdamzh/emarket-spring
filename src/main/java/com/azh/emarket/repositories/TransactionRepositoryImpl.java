package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.exceptions.BadRequestException;
import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private static final String SQL_ADD_TRANSACTION = "INSERT INTO TRANSACTION(CUSTOMER_ID, PAYMENT_ID, TOTAL_PAY) " +
            "VALUES(?,?,?)";

    private static final String SQL_FIND_TRANSACTION = "SELECT T.ID, T.TIMESTAMP, T.TOTAL_PAY, T.PAYMENT_ID, P.NAME AS PAYMENT_NAME FROM " +
            "TRANSACTION T JOIN PAYMENT P ON P.ID=T.PAYMENT_ID WHERE T.ID=?";

    private static final String SQL_FIND_TRANSACTION_BY_CUSTOMER = "SELECT T.ID, T.TIMESTAMP, T.TOTAL_PAY, T.PAYMENT_ID, P.NAME AS PAYMENT_NAME, T.CUSTOMER_ID FROM " +
            "TRANSACTION T JOIN PAYMENT P ON P.ID=T.PAYMENT_ID WHERE T.CUSTOMER_ID=?";

    private static final String SQL_ADD_TRANSACTION_PRODUCT = "INSERT INTO TRANSACTION_PRODUCT(TRANSACTION_ID, PRODUCT_ID, PRODUCT_AMOUNT) " +
            "VALUES(?,?,?)";

    private static final String SQL_FIND_ALL_PAYMENT = "SELECT ID, NAME FROM PAYMENT";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer insertTransaction(int customerId, int paymentId, Integer totalPay) throws BadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_ADD_TRANSACTION, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setInt(2, paymentId);
                ps.setInt(3, totalPay);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch (Exception e){
            throw new BadRequestException("Invalid. Failed transaction");
        }
    }

    @Override
    public Transaction findTransactionById(int id) {
        return jdbcTemplate.queryForObject(SQL_FIND_TRANSACTION, new Object[]{id}, transactionRowMapper);
    }

    @Override
    public List<Transaction> findTransactionByCustomer(int customerId) {
        return jdbcTemplate.query(SQL_FIND_TRANSACTION_BY_CUSTOMER, new Object[]{customerId}, transactionRowMapper);
    }

    @Override
    public void insertTransactionProduct(int transactionId, int productId, int productAmount) {
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_ADD_TRANSACTION_PRODUCT);
                ps.setInt(1, transactionId);
                ps.setInt(2, productId);
                ps.setInt(3, productAmount);
                return ps;
            });
        }catch (Exception e){
            throw new BadRequestException("Invalid. Failed transaction");
        }
    }

    @Override
    public List<Payment> findAllPayment() {
        return jdbcTemplate.query(SQL_FIND_ALL_PAYMENT, paymentRowMapper);
    }

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> {
      return new Transaction(
              rs.getInt("ID"),
              rs.getTimestamp("TIMESTAMP"),
              rs.getInt("TOTAL_PAY"),
              new Payment(rs.getInt("PAYMENT_ID"), rs.getString("PAYMENT_NAME"))
      );
    };

    private final RowMapper<Payment> paymentRowMapper = (rs, rowNum) -> {
        return new Payment(
                rs.getInt("ID"),
                rs.getString("NAME")
        );
    };
}
