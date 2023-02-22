package com.azh.emarket.models;

public class Cart {
    private Product product;
    private int productAmount;

    public Cart(Product product, int productAmount) {
        this.product = product;
        this.productAmount = productAmount;
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
