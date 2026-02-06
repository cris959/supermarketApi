package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    /**
     * Recupera todas las ventas activas junto con sus relaciones (Sucursal, Ítems y Productos).
     * Utiliza 'JOIN FETCH' para optimizar la consulta y evitar el problema de rendimiento N+1.
     * @return Lista de ventas activas con detalles cargados.
     */
    @Query("SELECT DISTINCT s FROM Sale s JOIN FETCH s.branch JOIN FETCH s.items i JOIN FETCH i.product")
    List<Sale> findAllWithDetails();

    /**
     * Calcula la suma total de dinero recaudado por una sucursal específica considerando solo ventas activas.
     * @param branchId El ID de la sucursal.
     * @return La suma total de las ventas (Double), o null si no hay ventas.
     */
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.branch.id = :branchId AND s.active = true")
    Double sumTotalByBranch(Long branchId);

    /**
     * Busca una venta específica por su ID, cargando inmediatamente sus ítems y productos asociados.
     * Útil para operaciones de actualización o cancelación que requieren los datos de los ítems.
     * @param id El ID de la venta.
     * @return Un Optional conteniendo la venta con detalles, o vacío si no se encuentra.
     */
    @Query("SELECT s FROM Sale s JOIN FETCH s.items i JOIN FETCH i.product WHERE s.id = :id")
    Optional<Sale> findByIdWithProduct(@Param("id") Long id);

    /**
     * Realiza una consulta nativa para obtener una proyección de ventas que han sido anuladas/archivadas (active = false).
     * Incluye datos básicos de la venta y el nombre de la sucursal.
     * @return Lista de proyecciones SaleProjection.
     */
    @Query(value = "SELECT s.id, s.date, s.status, s.total, s.branch_id as branchId, b.name as branchName FROM sales s JOIN branches b ON s.branch_id = b.id WHERE s.active = false", nativeQuery = true)
    List<SaleProjection> findArchivedSalesProjections();

    /**
     * Recupera los detalles de los ítems de una venta específica mediante una consulta nativa SQL.
     * Une la tabla 'order_items' con 'products' para obtener el nombre del producto.
     * Devuelve una lista de arrays de objetos (raw data) para un mapeo manual.
     * @param saleId El ID de la venta.
     * @return Lista de filas (Object[]) con: id, productName, quantity, unitPrice, subTotal.
     */
    @Query(value =
            "SELECT i.id, p.name, i.quantity, i.unit_price, " +
                    "(i.quantity * i.unit_price) " +
                    "FROM order_items i " +
                    "JOIN products p ON i.product_id = p.id " +
                    "WHERE i.sale_id = :saleId",
            nativeQuery = true)
    List<Object[]> findItemsBySaleIdRaw(@Param("saleId") Long saleId);

    /**
     * Calcula la suma total de dinero recaudado de todas las sucursales
     * considerando solo ventas activas.
     * @return La suma total de las ventas (Double), o 0.0 si no hay ventas.
     */
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.active = true")
    Double sumTotalAllBranches();
}
