package com.javabootcamp.assessment2.datamocks;

import com.javabootcamp.assessment2.entities.User;

public class AuthDataMock {

    public static User createMocks() {
        var user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        return user;
    }
}
