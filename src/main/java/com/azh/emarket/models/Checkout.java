package com.azh.emarket.models;

import java.sql.Timestamp;

public class Checkout {
    private Timestamp timestamp;
    private Product product;
    private int productAmount;

    public Checkout(Timestamp timestamp, Product product, int productAmount) {
        this.timestamp = timestamp;
        this.product = product;
        this.productAmount = productAmount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

}
