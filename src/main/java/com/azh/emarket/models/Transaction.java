package com.azh.emarket.models;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private Timestamp timestamp;
    private Integer totalPay;
    private Payment payment;

    public Transaction(int id, Timestamp timestamp, Integer totalPay, Payment payment) {
        this.id = id;
        this.timestamp = timestamp;
        this.totalPay = totalPay;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Integer totalPay) {
        this.totalPay = totalPay;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
