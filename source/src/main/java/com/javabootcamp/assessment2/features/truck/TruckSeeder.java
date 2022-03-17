package com.javabootcamp.assessment2.features.truck;

import com.javabootcamp.assessment2.entities.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TruckSeeder {

    public static final String NAME = "Toyota";

    @Autowired
    private TruckRepository truckRepository;

    public void createMocks() {
        var truckMock = new Truck();
        truckMock.setName(NAME);
        truckRepository.save(truckMock);
    }
}
