package com.azh.emarket.services;

import com.azh.emarket.exceptions.ResourceNotFoundException;
import com.azh.emarket.models.Product;
import com.azh.emarket.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> fetchAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> fetchByCategory(String category) throws ResourceNotFoundException {
        return productRepository.findByCategory(category);
    }
}
