package com.javabootcamp.assessment2.features.dashboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardResponse {
    private String truckLatitude;
    private String truckLongitude;

    private String cashCenterLatitude;
    private String cashCenterLongitude;

    private double totalBalances;
}
