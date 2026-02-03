package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.SaleDTO;

import java.util.List;

public interface ISaleService {

    List<SaleDTO> getSales();
    SaleDTO createSale(SaleDTO saleDTO);
    SaleDTO updateSale(Long id, SaleDTO saleDTO);
    void deleteSale(Long id);

    List<SaleDTO> getArchivedSales();
}
