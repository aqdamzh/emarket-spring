package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;

public interface CustomerRepository {
    Integer create(String name, String email) throws AuthException;
    Boolean findCredentials(String email, String password) throws AuthException;

    String createCredentials(String email, String password) throws AuthException;

    Customer findByEmail(String email) throws AuthException;

    Customer findById(Integer id);
}
