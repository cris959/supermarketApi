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


    // 1. Listar solo activos
    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllActive() {
        return ResponseEntity.ok(branchService.getBranches());
    }

    // 2. Buscar sucursal por nombre
    @GetMapping("/search")
    public ResponseEntity<List<BranchDTO>> searchByAddress(@RequestParam String address) {
        return ResponseEntity.ok(branchService.searchByAddress(address));
    }


    // 3. Obtener absolutamente todos
    @GetMapping("/all")
    public ResponseEntity<List<BranchDTO>> getAllIncludingInactive() {
        return ResponseEntity.ok(branchService.getAllBranchesIncludingInactive());
    }

    // 4. Obtener solo los archivados/borrados
    @GetMapping("/archived")
    public ResponseEntity<List<BranchDTO>> getArchived() {
        return ResponseEntity.ok(branchService.getArchivedBranches());
    }

    // 5. Obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    // 6. Crear sucursal
    @PostMapping
    public ResponseEntity<BranchDTO> create(@RequestBody BranchDTO branchDTO) {
        return new ResponseEntity<>(branchService.createBranch(branchDTO), HttpStatus.CREATED);
    }

    // 7. Actualizar sucursal
    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> update(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        return ResponseEntity.ok(branchService.updateBranch(id, branchDTO));
    }

    // 8. Borrado Lógico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
