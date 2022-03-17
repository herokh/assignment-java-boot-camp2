package com.javabootcamp.assessment2.features.asset;

import com.javabootcamp.assessment2.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    @Async
    CompletableFuture<List<Asset>> findAllByInsertedDate(Date insertedDate);
    List<Asset> findAllByShipmentId(UUID shipmentId);
}
