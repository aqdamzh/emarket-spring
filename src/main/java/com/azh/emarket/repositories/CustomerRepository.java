package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;

public interface CustomerRepository {
    Integer create(String name, String email, String password) throws AuthException;
    Customer findByEmailAndPassword(String email, String password) throws AuthException;
    Customer findById(Integer id);
}
