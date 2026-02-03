package com.cris959.SupermarketApi.controller;

import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // 1. Obtener solo los activos (Usa el findAll estándar con @SQLRestriction)
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllActive() {
        return ResponseEntity.ok(productService.getProducts());
    }

    // 2. Obtener ABSOLUTAMENTE TODOS (Tu Opción A)
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllIncludingInactive() {
        return ResponseEntity.ok(productService.getAllProductsIncludingInactive());
    }

    // 3. Obtener solo los archivados/borrados
    @GetMapping("/archived")
    public ResponseEntity<List<ProductDTO>> getArchived() {
        return ResponseEntity.ok(productService.getArchivedProducts());
    }

    // 4. Obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 5. Crear producto
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    // 6. Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    // 7. Borrado Lógico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
