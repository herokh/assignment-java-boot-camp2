package com.javabootcamp.assessment2.features.driver;

import com.javabootcamp.assessment2.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
