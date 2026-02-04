package com.cris959.SupermarketApi.controller;

import com.cris959.SupermarketApi.dto.SaleDTO;
import com.cris959.SupermarketApi.service.ISaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }


    // 1. Registrar una nueva venta (Procesa stock y total)
    @PostMapping
    public ResponseEntity<SaleDTO> createSale(@RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
    }

    // 2. Listar ventas activas
    @GetMapping
    public ResponseEntity<List<SaleDTO>> getSales() {
        return ResponseEntity.of(Optional.ofNullable(saleService.getSales()));
    }

    // 3. Ver una venta por ID (Detalle de ticket)
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    // 4. Anular una venta (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Ver ventas anuladas
    @GetMapping("/archived")
    public ResponseEntity<List<SaleDTO>> getArchived() {
        return ResponseEntity.ok(saleService.getArchivedSales());
    }

    // 6. Reporte: Total recaudado por sucursal
    @GetMapping("/reports/total-branch/{branchId}")
    public ResponseEntity<Double> getTotalByBranch(@PathVariable Long branchId) {
        return ResponseEntity.ok(saleService.getTotalSalesByBranch(branchId));
    }

}
