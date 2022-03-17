package com.javabootcamp.assessment2.features.branch;

import com.javabootcamp.assessment2.entities.Address;
import com.javabootcamp.assessment2.entities.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchSeeder {
    public static final String NAME = "B1";

    @Autowired
    private BranchRepository branchRepository;

    public void createMocks() {
        Branch branch = new Branch();
        branch.setName(NAME);
        Address address = new Address();
        address.setLatitude("101");
        address.setLongitude("010");
        branch.setAddress(address);
        branchRepository.save(branch);
    }
}
