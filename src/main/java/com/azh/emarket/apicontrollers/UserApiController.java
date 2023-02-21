package com.azh.emarket.apicontrollers;

import com.azh.emarket.EncryptData;
import com.azh.emarket.models.Customer;
import com.azh.emarket.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Map<String, Object> customerMap){
        String name = String.valueOf(customerMap.get("name"));
        String email = String.valueOf(customerMap.get("email"));
        String password = String.valueOf(customerMap.get("password"));
        Customer customer = customerService.register(name, email, EncryptData.generate(password));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody Map<String, Object> customerMap){
        String email = String.valueOf(customerMap.get("email"));
        String password = String.valueOf(customerMap.get("password"));
        Customer customer = customerService.validate(email, EncryptData.generate(password));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
