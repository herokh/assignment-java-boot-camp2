package com.javabootcamp.assessment2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Asset {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double amount;
    private String currency;

    @Temporal(TemporalType.DATE)
    private Date insertedDate;

    @Temporal(TemporalType.DATE)
    private Date updatedDate;
    private UUID shipmentId;
}
