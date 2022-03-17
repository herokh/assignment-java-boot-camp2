package com.javabootcamp.assessment2.features.truck;

import com.javabootcamp.assessment2.entities.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {
}
