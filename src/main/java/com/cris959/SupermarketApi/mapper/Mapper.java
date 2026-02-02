package com.cris959.SupermarketApi.mapper;


import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.dto.OrderItemDTO;
import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.dto.SaleDTO;
import com.cris959.SupermarketApi.model.Branch;
import com.cris959.SupermarketApi.model.Product;
import com.cris959.SupermarketApi.model.Sale;

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
        // 1. Convertimos la lista de items y calculamos el total en un solo paso
        var items = sale.getItems().stream().map(item-> new OrderItemDTO(
                item.getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getQuantity() * item.getUnitPrice()
        ))
                .toList();
        // 2. Calculamos el total desde la lista ya mapeada
        double total = items.stream()
                .mapToDouble(OrderItemDTO::subTotal)
                .sum();

// 3. Retornamos el Record SaleDTO usando su constructor
        return new SaleDTO(
                sale.getId(),
                sale.getDate(),
                sale.getStatus(),
                sale.getBranch().getId(),
                sale.getBranch().getName(),
                items,
                sale.getTotal()
        );
    }

    // Mapeo de Sucursal a SucuarsalDTO
    public static BranchDTO toDto(Branch branch) {
        if (branch == null) return null;
        return new BranchDTO(
                branch.getId(),
                branch.getName(),
                branch.getAddress()
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
        return product;
    }
}
