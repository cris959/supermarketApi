package com.cris959.SupermarketApi.service;

import com.cris959.SupermarketApi.dto.BranchDTO;
import com.cris959.SupermarketApi.exception.NotFoundException;
import com.cris959.SupermarketApi.mapper.Mapper;
import com.cris959.SupermarketApi.model.Branch;
import com.cris959.SupermarketApi.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {

    private final BranchRepository repository;

    public BranchService(BranchRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BranchDTO> getBranches() {
        return repository.findAll()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }

    @Override
    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = Mapper.toEntity(branchDTO);

        Branch savedBranch = repository.save(branch);

        return Mapper.toDTO(savedBranch);
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO) {
        Branch branch = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Branch not found with ID: " + id));
        branch.setName(branchDTO.name());
        branch.setAddress(branchDTO.address());

        Branch updateBranch = repository.save(branch);
        return Mapper.toDTO(updateBranch);
    }

    @Override
    public void deleteBranch(Long id) {
        Branch branch = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Branch not found to be deleted!"));
        // Automática (con @SQLDelete en la entidad)
        repository.delete(branch);
    }

    public List<BranchDTO> getArchivedBranches() {
        return repository.findInactiveBranches()
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }
}
