package com.javabootcamp.assessment2.features.driver;

import com.javabootcamp.assessment2.entities.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverSeeder {

    public static final String NAME = "Hero";

    @Autowired
    private DriverRepository driverRepository;

    public void createMocks() {
        Driver driver = new Driver();
        driver.setName(NAME);
        driverRepository.save(driver);
    }
}
