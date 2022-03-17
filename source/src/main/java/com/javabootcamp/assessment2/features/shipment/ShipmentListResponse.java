package com.javabootcamp.assessment2.features.shipment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShipmentListResponse {
    private List<ShipmentItemResponse> result;
}
