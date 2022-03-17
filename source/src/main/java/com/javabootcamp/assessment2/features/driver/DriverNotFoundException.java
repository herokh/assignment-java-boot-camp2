package com.javabootcamp.assessment2.features.driver;

public class DriverNotFoundException extends  RuntimeException {
    public DriverNotFoundException(String message) {
        super(message);
    }
}
