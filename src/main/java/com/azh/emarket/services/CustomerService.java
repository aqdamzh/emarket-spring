package com.azh.emarket.services;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;

public interface CustomerService {
    Customer validate(String email, String password) throws AuthException;
    Customer register(String name, String email, String password) throws AuthException;
}
