package com.javabootcamp.assessment2.features.accountingreport;

import com.javabootcamp.assessment2.entities.AccountingReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountingReportRepository extends JpaRepository<AccountingReport, UUID> {
}
