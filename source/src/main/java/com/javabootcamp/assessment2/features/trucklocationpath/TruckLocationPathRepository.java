package com.javabootcamp.assessment2.features.trucklocationpath;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.TruckLocationPath;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TruckLocationPathRepository extends JpaRepository<TruckLocationPath, UUID> {
    TruckLocationPath findFirst1ByShipmentOrderByInsertedDateDesc(Shipment shipment);
}
