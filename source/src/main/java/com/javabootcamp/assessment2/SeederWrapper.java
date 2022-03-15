package com.javabootcamp.assessment2;

import com.javabootcamp.assessment2.features.auth.AuthSeeder;
import org.springframework.beans.factory.annotation.Autowired;

public class SeederWrapper {

    @Autowired
    private AuthSeeder authSeeder;

    public void initDataMocks() {
        authSeeder.createUserMocks();
    }

}
