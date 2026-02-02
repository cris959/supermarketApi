package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.model.Branch;

import java.util.List;

public interface IBranchService {

    List<BranchDTO>getBranches();                            // Listar todas
    BranchDTO createBranch(BranchDTO branchDTO);             // Crear nueva
    BranchDTO updateBranch(Long id, BranchDTO branchDTO);    // Actualizar por ID
    void deleteBranch(Long id);                              // Eliminar por ID
}
