package com.javabootcamp.assessment2.features.trucklocationpath;

import com.javabootcamp.assessment2.entities.Address;
import com.javabootcamp.assessment2.enums.ShipmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreateTruckLocationPathRequest {
    private UUID deviceId;
    private String latitude;
    private String longitude;
}
