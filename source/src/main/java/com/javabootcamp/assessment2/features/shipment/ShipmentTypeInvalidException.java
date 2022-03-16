package com.javabootcamp.assessment2.features.shipment;

public class ShipmentTypeInvalidException extends RuntimeException {
    public ShipmentTypeInvalidException(String message) {
        super(message);
    }
}
