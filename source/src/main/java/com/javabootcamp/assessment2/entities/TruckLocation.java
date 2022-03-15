package com.javabootcamp.assessment2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TruckLocation {

    public TruckLocation() {}

    public TruckLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String latitude;
    private String longitude;
}
