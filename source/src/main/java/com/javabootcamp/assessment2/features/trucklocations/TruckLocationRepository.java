package com.javabootcamp.assessment2.features.trucklocations;

import com.javabootcamp.assessment2.entities.TruckLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckLocationRepository extends JpaRepository<TruckLocation, UUID> {
}
