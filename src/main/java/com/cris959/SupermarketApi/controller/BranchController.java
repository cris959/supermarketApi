package com.cris959.SupermarketApi.controller;


import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.service.IBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final IBranchService branchService;

    public BranchController(IBranchService branchService) {
        this.branchService = branchService;
    }

    // 1. Obtener solo los activos
    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllActive() {  // @SQLRestriction("active = true") siempre traerá solo los activos.
        return ResponseEntity.ok(branchService.getBranches());
    }

    // 2. Obtener ABSOLUTAMENTE TODOS
    @GetMapping("/all")
    public ResponseEntity<List<BranchDTO>> getAllIncludingInactive() {
        return ResponseEntity.ok(branchService.getAllBranchesIncludingInactive());
    }

    // 3. Obtener solo los archivados/borrados
    @GetMapping("/archived")
    public ResponseEntity<List<BranchDTO>> getArchived() {
        return ResponseEntity.ok(branchService.getArchivedBranches());
    }

    // 4. Obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    // 5. Crear sucursal
    @PostMapping
    public ResponseEntity<BranchDTO> create(@RequestBody BranchDTO branchDTO) {
        return new ResponseEntity<>(branchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    // 6. Actualizar sucursal
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> update(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO));
    }

    // 7. Borrado Lógico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
