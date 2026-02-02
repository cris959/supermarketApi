package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
