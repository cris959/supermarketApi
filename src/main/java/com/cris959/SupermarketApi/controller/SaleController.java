package com.cris959.SupermarketApi.controller;

import com.cris959.SupermarketApi.dto.BranchReportDTO;
import com.cris959.SupermarketApi.dto.GlobalReportDTO;
import com.cris959.SupermarketApi.dto.SaleDTO;
import com.cris959.SupermarketApi.service.ISaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "Endpoints for managing sales and reports")
@SecurityRequirement(name = "Bearer Authentication") // Requires JWT Token
public class SaleController {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }


    // 1. Registrar una nueva venta (Procesa stock y total)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Register a new sale", description = "Processes a new sale, updating inventory and calculating the total.")
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
    }

    // 2. Listar ventas activas
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List active sales", description = "Retrieves all sales that have not been canceled.")
    public ResponseEntity<List<SaleDTO>> getSales() {
        return ResponseEntity.of(Optional.ofNullable(saleService.getSales()));
    }

    // 3. Ver una venta por ID (Detalle de ticket)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get sale by ID", description = "Retrieves detailed information for a specific sale.")
    public ResponseEntity<SaleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    // 4. Anular una venta (Borrado lógico)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cancel a sale", description = "Performs a logical deletion of a sale, reversing its effect on inventory.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.cancelSale(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Ver ventas anuladas
    @GetMapping("/archived")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "View canceled sales", description = "Retrieves all sales that have been marked as canceled.")
    public ResponseEntity<List<SaleDTO>> getArchived() {
        return ResponseEntity.ok(saleService.getArchivedSales());
    }

    // 6. Reporte: Total recaudado por sucursal
    @GetMapping("/reports/total-branch/{branchId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Branch total revenue report", description = "Calculates total revenue for a specific branch.")
    public ResponseEntity<BranchReportDTO> getTotalByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(saleService.getTotalSalesByBranch(branchId));
    }

    // 7. Suma total de todas las Branches
    @GetMapping("/reports/total-all-branches")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Global total revenue report", description = "Calculates total revenue across all branches.")
    public ResponseEntity<GlobalReportDTO> getTotalAllBranches() {
        return ResponseEntity.ok(saleService.getTotalSalesAllBranches());
    }
}
