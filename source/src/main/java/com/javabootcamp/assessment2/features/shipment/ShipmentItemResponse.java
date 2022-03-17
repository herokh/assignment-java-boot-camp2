package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.enums.ShipmentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ShipmentItemResponse {
    private UUID id;

    private UUID senderId;
    private UUID receiverId;
    private ShipmentType shipmentType;

    private Date inserted;
    private UUID insertedBy;
}
