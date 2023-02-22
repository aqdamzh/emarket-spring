package com.azh.emarket.services;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Cart;
import com.azh.emarket.models.Checkout;

import java.util.List;

public interface CartService {
    List<Cart> fetchCartCustomer(int customerId) throws ResourceNotFoundException;
    Cart addToCart(int customerId, int productId, int productAmount);

    Cart deleteFromCart(int customerId, int productId);

    List<Checkout> checkoutFromCart(int customerId);

    List<Checkout> listCheckout(int customerId);
}
