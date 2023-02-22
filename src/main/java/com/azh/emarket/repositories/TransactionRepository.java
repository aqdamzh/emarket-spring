package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.BadRequestException;
import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Checkout;
import com.azh.emarket.models.Payment;
import com.azh.emarket.models.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface TransactionRepository {
    Integer insertTransaction(int customerId, int paymentId, Integer totalPay) throws BadRequestException;
    Transaction findTransactionById(int id);

    List<Transaction> findTransactionByCustomer(int customerId);

    void insertTransactionProduct(int transactionId, int productId, int productAmount);

    List<Payment> findAllPayment();
}
