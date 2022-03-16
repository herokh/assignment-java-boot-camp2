package com.javabootcamp.assessment2.entities;

import com.javabootcamp.assessment2.features.trucklocationpath.CreateTruckLocationPathRequest;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TruckLocationPath {

    public TruckLocationPath() {}

    public TruckLocationPath(Shipment shipment, String latitude, String longitude) {
        this.shipment = shipment;
        this.latitude = latitude;
        this.longitude = longitude;
        this.insertedDate = new Date();
    }

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    private String latitude;
    private String longitude;

    private Date insertedDate;
}
