package com.azh.emarket.repositories;

import com.azh.emarket.models.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
