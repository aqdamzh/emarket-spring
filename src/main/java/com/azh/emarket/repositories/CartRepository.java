package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.BadRequestException;
import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Cart;
import com.azh.emarket.models.Checkout;

import java.util.List;

public interface CartRepository {
    List<Cart> findCartByCustomer(Integer customerId) throws ResourceNotFoundException;

    Cart findCartByCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException;
    boolean insertToCart(Integer customerId, Integer productId, Integer productAmount) throws BadRequestException;
    boolean updateProductAmount(Integer customerId, Integer productId, Integer productAmount) throws BadRequestException;
    Integer findCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException;

    boolean deleteCustomerProduct(Integer customerId, Integer productId) throws ResourceNotFoundException;

    boolean deleteCustomerCart(Integer customerId) throws ResourceNotFoundException;

    boolean insertIntoCheckout(Integer customerId, Integer productId, Integer productAmount);

    List<Checkout> findCheckoutByCustomer(Integer customerId) throws ResourceNotFoundException;

    boolean deleteCustomerCheckout(Integer customerId) throws ResourceNotFoundException;
}
