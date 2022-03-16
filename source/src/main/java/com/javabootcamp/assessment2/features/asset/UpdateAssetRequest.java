package com.javabootcamp.assessment2.features.asset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAssetRequest {
    private double amount;
    private String currency;
}
