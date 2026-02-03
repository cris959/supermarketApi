package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s JOIN FETCH s.branch")
    List<Sale> findAllWithDetails();

    @Query(value = "SELECT * FROM sales WHERE active = false", nativeQuery = true)
    List<Sale> findInactiveSales();
}
