package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
