package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.enums.ShipmentStatus;
import com.javabootcamp.assessment2.enums.ShipmentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ShipmentFullResponse {
    private UUID id;
    private UUID truckId;
    private UUID deviceId;
    private UUID driverId;

    private UUID senderId;
    private UUID receiverId;
    private ShipmentType shipmentType;

    private String originLat;
    private String originLng;
    private String destinationLat;
    private String destinationLng;

    private ShipmentStatus status;

    private Date inserted;
    private UUID insertedBy;
}
