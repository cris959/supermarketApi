package com.cris959.SupermarketApi.mapper;


import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.dto.OrderItemDTO;
import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.dto.SaleDTO;
import com.cris959.SupermarketApi.model.Branch;
import com.cris959.SupermarketApi.model.Product;
import com.cris959.SupermarketApi.model.Sale;

import java.util.List;

public class Mapper {

    // Mapeo de Producto a ProductoDTO
    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock()
        );

    }

    // Mapeo de Venta a VentaDTO
    public static SaleDTO toDTO(Sale sale) {
        if (sale == null) return null;
        List<OrderItemDTO> itemDTOs = sale.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        (item.getSubtotal()) // <--- SUBTOTAL DESDE OrderItem
                ))
                .toList();

        return new SaleDTO(
                sale.getId(),
                sale.getDate(),
                sale.getStatus(),
                sale.getBranch().getId(),
                sale.getBranch().getName(),
                itemDTOs,
                sale.getTotal()
        );
    }

    // Mapeo de Sucursal a SucuarsalDTO
    public static BranchDTO toDTO(Branch branch) {
        if (branch == null) return null;
        return new BranchDTO(
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.isActive()
        );
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        // No seteamos el ID porque es una creación (autoincremental)
        product.setName(dto.name());
        product.setCategory(dto.category());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setActive(true);

        return product;
    }

    public static Branch toEntity(BranchDTO branchDTO) {
        if (branchDTO == null) return null;
        Branch branch = new Branch();
        // 1. Usamos SET para asignar los valores que vienen del DTO
        branch.setName(branchDTO.name());
        branch.setAddress(branchDTO.address());

        // El ID no se setea aquí si es una creación nueva (autoincremental)
        return branch;
    }
}
