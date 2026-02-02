package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.BranchDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {
    @Override
    public List<BranchDTO> getBranches() {
        return List.of();
    }

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        return null;
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        return null;
    }

    @Override
    public void deleteBranch(Long id) {

    }
}
