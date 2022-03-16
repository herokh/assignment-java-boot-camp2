package com.javabootcamp.assessment2.features.assets;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAssetRequest {
    private UUID shipmentId;
    private double amount;
    private String currency;
}
