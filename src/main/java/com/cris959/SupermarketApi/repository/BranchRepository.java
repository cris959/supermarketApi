package com.cris959.SupermarketApi.repository;

import com.cris959.SupermarketApi.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query(value = "SELECT * FROM branches WHERE active = false", nativeQuery = true)
    List<Branch> findInactiveBranches();

    @Query(value = "SELECT * FROM branches", nativeQuery = true)
    List<Branch> findAllIncludingInactive();

}
