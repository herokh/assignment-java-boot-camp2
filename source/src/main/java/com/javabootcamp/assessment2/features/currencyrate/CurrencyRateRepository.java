package com.javabootcamp.assessment2.features.currencyrate;

import com.javabootcamp.assessment2.entities.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, UUID> {
}
