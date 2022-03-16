package com.javabootcamp.assessment2.features.asset;

import com.javabootcamp.assessment2.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findAllByInsertedDate(Date insertedDate);
    List<Asset> findAllByShipmentId(UUID shipmentId);
}
