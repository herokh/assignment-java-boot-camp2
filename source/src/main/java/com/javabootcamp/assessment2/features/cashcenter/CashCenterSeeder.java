package com.javabootcamp.assessment2.features.cashcenter;

import com.javabootcamp.assessment2.entities.Address;
import com.javabootcamp.assessment2.entities.CashCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashCenterSeeder {
    public static final String NAME = "C1";

    @Autowired
    private CashCenterRepository cashCenterRepository;

    public void createMocks() {
        CashCenter cashCenter = new CashCenter();
        cashCenter.setName(NAME);
        Address address = new Address();
        address.setLatitude("101");
        address.setLongitude("010");
        cashCenter.setAddress(address);
        cashCenterRepository.save(cashCenter);
    }
}
