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

    private String originLat;
    private String originLng;

    private String destinationLat;
    private String destinationLng;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "truck_id")
    private Truck truck;

    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private UUID senderId;
    private UUID receiverId;
    private ShipmentType shipmentType;

    private Date insertedDate;
    private UUID insertedBy;
}