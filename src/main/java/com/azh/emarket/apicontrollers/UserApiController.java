package com.azh.emarket.apicontrollers;

import com.azh.emarket.EncryptData;
import com.azh.emarket.filters.JWTConstants;
import com.azh.emarket.models.Customer;
import com.azh.emarket.services.CustomerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> customerMap){
        String name = String.valueOf(customerMap.get("name"));
        String email = String.valueOf(customerMap.get("email"));
        String password = String.valueOf(customerMap.get("password"));
        Customer customer = customerService.register(name, email, EncryptData.generate(password));
        HashMap<String, String> response = new HashMap<>();
        response.put("name", customer.getName());
        response.put("email", customer.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, Object> customerMap){
        String email = String.valueOf(customerMap.get("email"));
        String password = String.valueOf(customerMap.get("password"));
        Customer customer = customerService.validate(email, EncryptData.generate(password));
        return new ResponseEntity<>(generateJWTToken(customer), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(Customer customer) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, JWTConstants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + JWTConstants.TOKEN_VALIDITY))
                .claim("customerId", customer.getId())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
