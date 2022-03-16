package com.javabootcamp.assessment2.entities;

import com.javabootcamp.assessment2.enums.ShipmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Shipment {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "origin_id")
    private Address origin;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Address destination;

    private UUID senderId;
    private UUID receiverId;
    private UUID driverId;
    private UUID truckId;
    private UUID ioTdeviceId;
    private ShipmentType shipmentType;

    private Date insertedDate;
    private UUID insertedBy;

    private Date updatedDate;
    private UUID updatedBy;
}