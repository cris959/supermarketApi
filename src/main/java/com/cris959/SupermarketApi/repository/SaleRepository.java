package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // 1. Trae ventas activas con sucursal e ítems en una sola consulta (Evita el N+1) JPQL
    @Query("SELECT DISTINCT s FROM Sale s JOIN FETCH s.branch JOIN FETCH s.items i JOIN FETCH i.product")
    List<Sale> findAllWithDetails();

    // 2. Trae solo las anuladas (Native Query)
    @Query(value = "SELECT * FROM sales WHERE active = false", nativeQuery = true)
    List<Sale> findInactiveSales();

    // 3. Trae el historial (Native Query)
    @Query(value = "SELECT * FROM sales", nativeQuery = true)
    List<Sale> findAllIncludingInactive();

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.branch.id = :branchId AND s.active = true")
    Double sumTotalByBranch(Long branchId);
}
