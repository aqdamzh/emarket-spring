package com.azh.emarket.apicontrollers;

import com.azh.emarket.models.Payment;
import com.azh.emarket.models.Transaction;
import com.azh.emarket.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionApiController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactionByCustomer(HttpServletRequest request){
        int customerId = (int) request.getAttribute("customerId");
        List<Transaction> transactions = transactionService.listTransactionByCustomer(customerId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/payment")
    public ResponseEntity<List<Payment>> listPayments(HttpServletRequest request){
        int customerId = (int) request.getAttribute("customerId");
        List<Payment> payments = transactionService.listPayment();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PostMapping("/payment")
    public ResponseEntity<Transaction> processTransaction(HttpServletRequest request, @RequestBody Map<String, Object> transactionMap){
        int customerId = (int) request.getAttribute("customerId");
        int paymentId = (int) transactionMap.get("paymentId");
        Transaction transaction = transactionService.addTransaction(customerId, paymentId);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
