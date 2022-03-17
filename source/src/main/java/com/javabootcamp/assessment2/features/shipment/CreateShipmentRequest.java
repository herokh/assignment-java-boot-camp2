package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.enums.ShipmentType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateShipmentRequest {
    private UUID senderId;
    private UUID receiverId;

    private UUID driverId;
    private UUID truckId;
    private UUID deviceId;

    private ShipmentType shipmentType;
}
