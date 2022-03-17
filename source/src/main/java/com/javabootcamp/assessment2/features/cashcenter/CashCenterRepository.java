package com.javabootcamp.assessment2.features.cashcenter;

import com.javabootcamp.assessment2.entities.CashCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CashCenterRepository extends JpaRepository<CashCenter, UUID> {
    CashCenter findByName(String name);
}
