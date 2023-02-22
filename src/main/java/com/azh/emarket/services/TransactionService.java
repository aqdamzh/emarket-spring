package com.azh.emarket.services;

import com.azh.emarket.models.Payment;
import com.azh.emarket.models.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction addTransaction(int customerId, int paymentId);

    List<Transaction> listTransactionByCustomer(int customerId);

    List<Payment> listPayment();
}
