package com.azh.emarket.services;

import com.azh.emarket.exceptions.AuthException;
import com.azh.emarket.models.Customer;
import com.azh.emarket.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer validate(String email, String password) throws AuthException {
        if(email!=null) email = email.toLowerCase();
        return customerRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Customer register(String name, String email, String password) throws AuthException {
        if(email!=null) email = email.toLowerCase();
        Integer customerId = customerRepository.create(name, email, password);
        return customerRepository.findById(customerId);
    }
}
