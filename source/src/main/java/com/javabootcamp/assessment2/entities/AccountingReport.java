package com.javabootcamp.assessment2.entities;

import com.javabootcamp.assessment2.enums.BatchStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AccountingReport {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Date adjustDate;
    private BatchStatus status;
    private String errorReason;
}
