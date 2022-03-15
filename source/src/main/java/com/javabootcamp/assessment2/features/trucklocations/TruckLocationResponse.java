package com.javabootcamp.assessment2.features.trucklocations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TruckLocationResponse {
    public TruckLocationResponse(Boolean success) {
        this.success = success;
    }

    private Boolean success;
}
