package com.novatec.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novatec.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
    
}
