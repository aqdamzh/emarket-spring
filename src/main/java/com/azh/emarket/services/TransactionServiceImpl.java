package com.azh.emarket.services;

import com.azh.emarket.models.Checkout;
import com.azh.emarket.models.Payment;
import com.azh.emarket.models.Transaction;
import com.azh.emarket.repositories.CartRepository;
import com.azh.emarket.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CartRepository cartRepository;

    @Override
    public Transaction addTransaction(int customerId, int paymentId) {
        List<Checkout> checkouts = cartRepository.findCheckoutByCustomer(customerId);
        Integer totalPay = 0;
        for (Checkout ch:
             checkouts) {
            totalPay = totalPay + ch.getProduct().getPrice()*ch.getProductAmount();
        }
        Integer transactionId = transactionRepository.insertTransaction(customerId, paymentId, totalPay);
        for (Checkout ch:
                checkouts) {
            transactionRepository.insertTransactionProduct(transactionId, ch.getProduct().getId(), ch.getProductAmount());
        }
        cartRepository.deleteCustomerCheckout(customerId);
        return transactionRepository.findTransactionById(transactionId);
    }

    @Override
    public List<Transaction> listTransactionByCustomer(int customerId) {
        return transactionRepository.findTransactionByCustomer(customerId);
    }

    @Override
    public List<Payment> listPayment() {
        return transactionRepository.findAllPayment();
    }
}
