package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE active = false", nativeQuery = true)
    List<Product> findInactiveProducts();

    @Query(value = "SELECT * FROM products", nativeQuery = true)
    List<Product> findAllIncludingInactive();
}
