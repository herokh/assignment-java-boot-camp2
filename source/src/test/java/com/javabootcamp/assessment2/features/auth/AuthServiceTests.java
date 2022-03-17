package com.javabootcamp.assessment2.features.auth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    private AuthService testService;

    @BeforeEach
    void setup() {

    }

    @Test
    void test1() {
        assertEquals(1, 1);
    }
}
