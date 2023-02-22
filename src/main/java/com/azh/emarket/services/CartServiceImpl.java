package com.azh.emarket.services;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Cart;
import com.azh.emarket.models.Checkout;
import com.azh.emarket.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;


    @Override
    public List<Cart> fetchCartCustomer(int customerId) throws ResourceNotFoundException {
        return cartRepository.findCartByCustomer(customerId);
    }

    @Override
    public Cart addToCart(int customerId, int productId, int productAmount) {
        try {
            int cartProductAmount = cartRepository.findCustomerProduct(customerId, productId);
            cartRepository.updateProductAmount(customerId, productId, productAmount+cartProductAmount);
            return cartRepository.findCartByCustomerProduct(customerId,productId);
        }catch (Exception e){
            cartRepository.insertToCart(customerId, productId, productAmount);
            return cartRepository.findCartByCustomerProduct(customerId,productId);
        }
    }

    @Override
    public Cart deleteFromCart(int customerId, int productId) {
        Cart cart = cartRepository.findCartByCustomerProduct(customerId,productId);
        cartRepository.deleteCustomerProduct(customerId, productId);
        return cart;
    }

    @Override
    public List<Checkout> checkoutFromCart(int customerId) {
        List<Cart> carts = cartRepository.findCartByCustomer(customerId);
        for (Cart cart:
             carts) {
            cartRepository.insertIntoCheckout(customerId,cart.getProduct().getId(),cart.getProductAmount());
        }
        cartRepository.deleteCustomerCart(customerId);
        return cartRepository.findCheckoutByCustomer(customerId);
    }

    @Override
    public List<Checkout> listCheckout(int customerId) {
        return cartRepository.findCheckoutByCustomer(customerId);
    }
}
