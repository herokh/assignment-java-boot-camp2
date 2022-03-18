package com.javabootcamp.assessment2.features.trucklocationpath;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.features.shipment.ShipmentNotFoundException;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckLocationPathServiceTests {

    private TruckLocationPathService testService;

    @Mock
    private ShipmentRepository shipmentRepositoryMock;

    @Mock
    private TruckLocationPathRepository truckLocationPathRepositoryMock;

    @BeforeEach
    void setup() {

        testService = new TruckLocationPathService(shipmentRepositoryMock, truckLocationPathRepositoryMock);
    }

    @Test
    @DisplayName("Verify saveTruckLocationPath that should be success")
    void test1() {
        Shipment shipmentMock = new Shipment();
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(shipmentMock));

        var createTruckLocationPathRequest = new CreateTruckLocationPathRequest();
        createTruckLocationPathRequest.setShipmentId(UUID.randomUUID());
        createTruckLocationPathRequest.setLatitude("1234");
        createTruckLocationPathRequest.setLongitude("5678");
        var result = testService.saveTruckLocationPath(createTruckLocationPathRequest);

        assertTrue(result.isSuccess());
    }

    @Test
    @DisplayName("Verify saveTruckLocationPath that should be thrown ShipmentNotFoundException")
    void test2() {
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        var createTruckLocationPathRequest = new CreateTruckLocationPathRequest();
        createTruckLocationPathRequest.setShipmentId(UUID.randomUUID());
        createTruckLocationPathRequest.setLatitude("1234");
        createTruckLocationPathRequest.setLongitude("5678");
        assertThrows(ShipmentNotFoundException.class, () -> testService.saveTruckLocationPath(createTruckLocationPathRequest));
    }
}
