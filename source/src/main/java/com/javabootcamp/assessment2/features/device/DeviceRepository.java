package com.javabootcamp.assessment2.features.device;

import com.javabootcamp.assessment2.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
