package com.javabootcamp.assessment2.entities;

import com.javabootcamp.assessment2.enums.ShipmentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ShipmentStatusHistory {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private ShipmentStatus status;

    private Date insertedDate;
    private UUID insertedBy;
}
