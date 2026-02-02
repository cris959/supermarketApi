package com.cris959.SupermarketApi.service;


import com.cris959.SupermarketApi.dto.SaleDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService implements ISaleService {
    @Override
    public List<SaleDTO> getSales() {
        return List.of();
    }

    @Override
    public SaleDTO createSale(SaleDTO saleDTO) {
        return null;
    }

    @Override
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        return null;
    }

    @Override
    public void deleteSale(Long id) {

    }
}
