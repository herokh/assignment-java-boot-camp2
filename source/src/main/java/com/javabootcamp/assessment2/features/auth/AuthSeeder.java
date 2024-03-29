package com.javabootcamp.assessment2.features.auth;

import com.javabootcamp.assessment2.entities.User;
import com.javabootcamp.assessment2.enums.UserRole;
import com.javabootcamp.assessment2.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthSeeder {

    private final String DEFAULT_PASSWORD = "1234";

    @Autowired
    private AuthRepository authRepository;

    public void createMocks() {
        String hashPassword = BCryptUtil.hashString(DEFAULT_PASSWORD);

        var branch = new User("branch", hashPassword, UserRole.Branch);
        var cashCenter = new User("cashcenter", hashPassword, UserRole.CashCenter);

        var driver = new User("driver", hashPassword, UserRole.Driver);
        var ioT = new User("iot", hashPassword, UserRole.IoT);

        authRepository.save(driver);
        authRepository.save(branch);
        authRepository.save(cashCenter);
        authRepository.save(ioT);
    }
}
