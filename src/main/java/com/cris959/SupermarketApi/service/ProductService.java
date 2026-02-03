package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.exception.NotFoundException;
import com.cris959.SupermarketApi.mapper.Mapper;
import com.cris959.SupermarketApi.model.Product;
import com.cris959.SupermarketApi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 1. Convertimos DTO a Entidad
        Product product = Mapper.toEntity(productDTO);

        // 2. Guardamos la entidad
        Product savedProduct = repository.save(product);

        // 3. Devolvemos el DTO (usando toDTO con mayúsculas)
        return Mapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Vamos a buscar si existe ese producto
        Product product = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product not found with ID: " + id));
        // 2. Seteamos los datos que vienen del DTO (la fuente de la actualización)
        product.setName(productDTO.name());
        product.setCategory(productDTO.category());
        product.setPrice(productDTO.price());
        product.setStock(productDTO.stock());

        // 3. Guardamos y mapeamos de vuelta (Ojo con las mayúsculas: toDTO)
        Product updatedProduct = repository.save(product);
        return Mapper.toDTO(updatedProduct);
    }


    @Override
    public void deleteProduct(Long id) {
        // 1. Buscamos el producto
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found to be deleted!"));

        // 2. Opción A: Manual (si no usas @SQLDelete)
//        product.setActive(false);
//        repository.save(product);

        // 2. Opción B: Automática (si añadiste @SQLDelete en la entidad)
        repository.delete(product);
    }

    @Override
    public List<ProductDTO> getArchivedProducts() {
        return repository.findInactiveProducts()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProductsIncludingInactive() {
        // Llamamos al método con la Query Nativa que ya tenías
        return repository.findAllIncludingInactive()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }
}
