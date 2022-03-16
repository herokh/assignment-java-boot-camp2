package com.javabootcamp.assessment2.features.trucklocationpaths;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTruckLocationPathRequest {
    private UUID deviceId;
    private String latitude;
    private String longitude;
}
