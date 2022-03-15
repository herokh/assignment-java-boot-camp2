package com.javabootcamp.assessment2.features.trucklocationpaths;

import com.javabootcamp.assessment2.entities.TruckLocationPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckLocationPathRepository extends JpaRepository<TruckLocationPath, UUID> {
}
