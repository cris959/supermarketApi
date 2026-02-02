package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
