package com.javabootcamp.assessment2.features.trucklocationpaths;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TruckLocationPathResponse {
    public TruckLocationPathResponse(Boolean success) {
        this.success = success;
    }

    private Boolean success;
}
