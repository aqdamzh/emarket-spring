package com.azh.emarket.services;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> fetchAll();
    List<Product> fetchByCategory(String category) throws ResourceNotFoundException;
}
