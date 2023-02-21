package com.azh.emarket.repositories;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    List<Product> findByCategory(String category) throws ResourceNotFoundException;
}
