package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    // JOIN FETCH le dice a Hibernate que cargue el producto asociado inmediatamente
    @Query("SELECT s FROM Sale s JOIN FETCH s.items i JOIN FETCH i.product WHERE s.id = :id")
    Optional<Sale> findByIdWithProduct(@Param("id") Long id);

   // Para tarer los items de branches archived
    @Query(value = "SELECT s.id, s.date, s.status, s.total, s.branch_id as branchId, b.name as branchName FROM sales s JOIN branches b ON s.branch_id = b.id WHERE s.active = false", nativeQuery = true)
    List<SaleProjection> findArchivedSalesProjections();

    @Query(value =
            "SELECT i.id, p.name, i.quantity, i.unit_price, " +
                    "(i.quantity * i.unit_price) " +
                    "FROM order_items i " +
                    "JOIN products p ON i.product_id = p.id " +
                    "WHERE i.sale_id = :saleId",
            nativeQuery = true)
    List<Object[]> findItemsBySaleIdRaw(@Param("saleId") Long saleId);
}
