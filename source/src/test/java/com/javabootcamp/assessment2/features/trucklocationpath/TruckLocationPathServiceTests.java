package com.javabootcamp.assessment2.features.trucklocationpath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TruckLocationPathServiceTests {

    private TruckLocationPathService testService;

    @BeforeEach
    void setup() {

    }

    @Test
    void test1() {
        assertEquals(1, 1);
    }
}
