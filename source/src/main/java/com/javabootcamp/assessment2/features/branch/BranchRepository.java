package com.javabootcamp.assessment2.features.branch;

import com.javabootcamp.assessment2.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {
}
