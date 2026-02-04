package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.SaleDTO;

import java.util.List;

public interface ISaleService {

    // Lógica principal: Descuento de stock y cálculo de precios
    SaleDTO createSale(SaleDTO saleDTO);

    // Listar ventas activas
    List<SaleDTO> getSales();

    // Ver detalle de una venta específica
    SaleDTO getSaleById(Long id);


//    SaleDTO updateSale(Long id, SaleDTO saleDTO);

    void deleteSale(Long id);

    // Ver ventas anuladas
    List<SaleDTO> getArchivedSales();

    Double getTotalSalesByBranch(Long branchId);
}
