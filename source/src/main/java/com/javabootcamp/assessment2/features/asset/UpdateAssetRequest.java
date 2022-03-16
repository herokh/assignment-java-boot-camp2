package com.javabootcamp.assessment2.features.assets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAssetRequest {
    private double amount;
    private String currency;
}
