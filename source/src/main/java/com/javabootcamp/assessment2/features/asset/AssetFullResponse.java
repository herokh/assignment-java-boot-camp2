package com.javabootcamp.assessment2.features.asset;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AssetFullResponse {
    private UUID id;
    private double amount;
    private String currency;
}
