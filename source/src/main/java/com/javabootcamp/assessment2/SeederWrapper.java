package com.javabootcamp.assessment2;

import com.javabootcamp.assessment2.features.auth.AuthSeeder;
import com.javabootcamp.assessment2.features.branch.BranchSeeder;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterSeeder;
import com.javabootcamp.assessment2.features.device.DeviceSeeder;
import com.javabootcamp.assessment2.features.driver.DriverSeeder;
import com.javabootcamp.assessment2.features.truck.TruckSeeder;
import org.springframework.beans.factory.annotation.Autowired;

public class SeederWrapper {

    @Autowired
    private AuthSeeder authSeeder;
    @Autowired
    private TruckSeeder truckSeeder;
    @Autowired
    private DriverSeeder driverSeeder;
    @Autowired
    private DeviceSeeder deviceSeeder;
    @Autowired
    private BranchSeeder branchSeeder;
    @Autowired
    private CashCenterSeeder cashCenterSeeder;

    public void initDataMocks() {

        authSeeder.createMocks();
        truckSeeder.createMocks();
        driverSeeder.createMocks();
        deviceSeeder.createMocks();
        branchSeeder.createMocks();
        cashCenterSeeder.createMocks();

    }
}
