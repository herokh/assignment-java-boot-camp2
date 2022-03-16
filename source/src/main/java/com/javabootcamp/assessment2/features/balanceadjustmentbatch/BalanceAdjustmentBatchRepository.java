package com.javabootcamp.assessment2.features.balanceadjustmentbatch;

import com.javabootcamp.assessment2.entities.BalanceAdjustmentBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceAdjustmentBatchRepository extends JpaRepository<BalanceAdjustmentBatch, UUID> {
}
