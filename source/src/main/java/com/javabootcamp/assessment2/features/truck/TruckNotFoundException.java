package com.javabootcamp.assessment2.features.truck;

public class TruckNotFoundException extends RuntimeException {
    public TruckNotFoundException(String message) {
        super(message);
    }
}
