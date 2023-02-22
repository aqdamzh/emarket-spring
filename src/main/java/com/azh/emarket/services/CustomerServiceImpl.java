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
        if(customerRepository.findCredentials(email,password)){
            return customerRepository.findByEmail(email);
        }else{
            return null;
        }
    }

    @Override
    public Customer register(String name, String email, String password) throws AuthException {
        if(email!=null) email = email.toLowerCase();
        if(customerRepository.createCredentials(email, password)!=null){
            Integer customerId = customerRepository.create(name, email);
            return customerRepository.findById(customerId);
        }else{
            return null;
        }
    }
}
