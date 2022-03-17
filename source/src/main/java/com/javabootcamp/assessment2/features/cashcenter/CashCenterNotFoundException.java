package com.javabootcamp.assessment2.features.cashcenter;

public class CashCenterNotFoundException extends RuntimeException {
    public CashCenterNotFoundException(String message) {
        super(message);
    }
}
