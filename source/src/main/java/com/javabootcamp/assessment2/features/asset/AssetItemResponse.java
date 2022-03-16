package com.javabootcamp.assessment2.features.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AssetItemResponse {
    private UUID id;
    private double amount;
    private String currency;
}
