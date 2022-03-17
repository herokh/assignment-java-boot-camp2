package com.javabootcamp.assessment2.features.shipmentstatushistory;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, UUID> {
    ShipmentStatusHistory findFirst1ByShipmentOrderByInsertedDateDesc(Shipment shipment);
}
