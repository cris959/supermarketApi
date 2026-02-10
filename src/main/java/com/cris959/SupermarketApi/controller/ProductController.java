package com.cris959.SupermarketApi.controller;

import com.cris959.SupermarketApi.dto.ProductDTO;
import com.cris959.SupermarketApi.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management endpoints")
@SecurityRequirement(name = "Bearer Authentication") // Requiere Token JWT
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // 1. Obtener solo los activos (Usa el findAll estándar con @SQLRestriction)
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List active products", description = "Returns all products that have not been logically deleted.")
    public ResponseEntity<List<ProductDTO>> getAllActive() {  // @SQLRestriction("active = true") siempre traerá solo los activos.
        return ResponseEntity.ok(productService.getProducts());
    }

    // 2. Obtener ABSOLUTAMENTE TODOS
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List all products", description = "Returns all products, including inactive or archived ones.")
    public ResponseEntity<List<ProductDTO>> getAllIncludingInactive() {
        return ResponseEntity.ok(productService.getAllProductsIncludingInactive());
    }

    // 3. Obtener solo los archivados/borrados
    @GetMapping("/archived")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List archived products", description = "Returns only logically deleted products.")
    public ResponseEntity<List<ProductDTO>> getArchived() {
        return ResponseEntity.ok(productService.getArchivedProducts());
    }

    // 4. Obtener uno por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get product by ID", description = "Returns a specific product based on its ID.")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // 5. Crear producto
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new product", description = "It allows an administrator to create a new product.")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    // 6. Actualizar producto
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "Allows an administrator to update the data of an existing product.")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    // 7. Borrado Lógico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Logical product deletion", description = "Marks a product as inactive/archived without physically deleting it from the database.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 8. Buscar por nombre (Búsqueda parcial: "Coca" encuentra "Coca Cola")
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Search for product by name", description = "Searches for products that partially match the provided name.")
    public ResponseEntity<List<ProductDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    // 9. Filtrar por categoría
    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Filter by category", description = "Return all products that belong to a specific category.")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    // 10. Alerta de Stock Bajo (Para que el dueño sepa qué comprar)
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Low stock alert", description = "Returns products whose stock is below the specified threshold.")
    public ResponseEntity<List<ProductDTO>> getLowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(productService.getLowStockProducts(threshold));
    }
}
