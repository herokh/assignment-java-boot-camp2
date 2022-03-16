package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    Shipment findByDeviceId(UUID deviceId);
}
