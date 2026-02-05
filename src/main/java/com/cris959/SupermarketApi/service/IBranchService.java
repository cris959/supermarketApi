package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.BranchDTO;

import java.util.List;

public interface IBranchService {

    List<BranchDTO>getBranches();                            // Listar todas
    List<BranchDTO> getArchivedBranches();                   // Devuelve active = false
    List<BranchDTO> getAllBranchesIncludingInactive();       // Devuelve all
    List<BranchDTO> searchByAddress(String address);         // Vusca sucursales

    BranchDTO getBranchById(Long id);
    BranchDTO createBranch(BranchDTO branchDTO);
    BranchDTO updateBranch(Long id, BranchDTO branchDTO);
    void deleteBranch(Long id);
}
