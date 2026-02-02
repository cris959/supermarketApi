package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.mapper.Mapper;
import com.cris959.SupermarketApi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {


    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> getProducts() {
        return repository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
