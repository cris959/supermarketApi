package com.cris959.SupermarketApi.controller;


import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.service.IBranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branches", description = "Endpoints for branch management")
// Esto asegura que todos los métodos requieran el Token JWT en Swagger
@SecurityRequirement(name = "Bearer Authentication")
public class BranchController {

    private final IBranchService branchService;

    public BranchController(IBranchService branchService) {
        this.branchService = branchService;
    }


    // 1. Listar solo activos
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List active branches", description = "Returns all branches that have not been logically deleted.")
    public ResponseEntity<List<BranchDTO>> getAllActive() {
        return ResponseEntity.ok(branchService.getBranches());
    }

    // 2. Buscar sucursal por nombre
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Search for branch by address", description = "Searches for branches that partially match the provided address.")
    public ResponseEntity<List<BranchDTO>> searchByAddress(@RequestParam String address) {
        return ResponseEntity.ok(branchService.searchByAddress(address));
    }


    // 3. Obtener absolutamente todos
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List all branches", description = "Returns all branches, including inactive or archived ones.")
    public ResponseEntity<List<BranchDTO>> getAllIncludingInactive() {
        return ResponseEntity.ok(branchService.getAllBranchesIncludingInactive());
    }

    // 4. Obtener solo los archivados/borrados
    @GetMapping("/archived")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "List archived branches", description = "Returns only logically deleted branches.")
    public ResponseEntity<List<BranchDTO>> getArchived() {
        return ResponseEntity.ok(branchService.getArchivedBranches());
    }

    // 5. Obtener uno por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get branch by ID", description = "Returns a specific branch based on its ID.")
    public ResponseEntity<BranchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    // 6. Crear sucursal
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create new branch", description = "Allows an administrator to create a new branch.")
    public ResponseEntity<BranchDTO> create(@RequestBody BranchDTO branchDTO) {
        return new ResponseEntity<>(branchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    // 7. Actualizar sucursal
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update branch", description = "Allows an administrator to update the data of an existing branch.")
    public ResponseEntity<BranchDTO> update(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO));
    }

    // 8. Borrado Lógico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Logical branch deletion", description = "Marks a branch as inactive/archived without deleting it from the database.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
