package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getProducts();
    ProductDTO getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);

    // Métodos de filtrado y búsqueda
    List<ProductDTO> getAllProductsIncludingInactive();
    List<ProductDTO> getArchivedProducts();
    List<ProductDTO> searchByName(String name);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getLowStockProducts(Integer threshold);
}
