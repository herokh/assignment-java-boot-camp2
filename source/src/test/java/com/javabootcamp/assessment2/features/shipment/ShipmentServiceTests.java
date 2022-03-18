package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.entities.*;
import com.javabootcamp.assessment2.enums.ShipmentStatus;
import com.javabootcamp.assessment2.enums.ShipmentType;
import com.javabootcamp.assessment2.features.branch.BranchRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.device.DeviceRepository;
import com.javabootcamp.assessment2.features.driver.DriverRepository;
import com.javabootcamp.assessment2.features.shipmentstatushistory.ShipmentStatusHistoryRepository;
import com.javabootcamp.assessment2.features.truck.TruckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTests {

    private ShipmentService testService;

    @Mock
    private DriverRepository driverRepositoryMock;
    @Mock
    private DeviceRepository deviceRepositoryMock;
    @Mock
    private TruckRepository truckRepositoryMock;
    @Mock
    private BranchRepository branchRepositoryMock;
    @Mock
    private CashCenterRepository cashCenterRepositoryMock;
    @Mock
    private ShipmentRepository shipmentRepositoryMock;
    @Mock
    private ShipmentStatusHistoryRepository shipmentStatusHistoryRepositoryMock;
    @Mock
    private ApplicationContext applicationContextMock;

    @BeforeEach
    void setup() {
        testService = new ShipmentService(
                driverRepositoryMock,
                deviceRepositoryMock,
                truckRepositoryMock,
                branchRepositoryMock,
                cashCenterRepositoryMock,
                shipmentRepositoryMock,
                shipmentStatusHistoryRepositoryMock,
                applicationContextMock);
    }

    @Test
    @DisplayName("Verify createShipment should be success")
    void test1() {
        var createShipmentRequest = new CreateShipmentRequest();
        createShipmentRequest.setShipmentType(ShipmentType.BranchToCashCenter);
        createShipmentRequest.setSenderId(UUID.randomUUID());
        createShipmentRequest.setReceiverId(UUID.randomUUID());
        createShipmentRequest.setTruckId(UUID.randomUUID());
        createShipmentRequest.setDriverId(UUID.randomUUID());
        createShipmentRequest.setDeviceId(UUID.randomUUID());

        var branch = new Branch();
        branch.setAddress(new Address());
        when(branchRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(branch));
        var cashCenter = new CashCenter();
        cashCenter.setAddress(new Address());
        when(cashCenterRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(cashCenter));
        when(driverRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(new Driver()));
        when(deviceRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(new Device()));
        when(truckRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(new Truck()));
        var shipment = new Shipment();
        shipment.setId(UUID.randomUUID());
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(shipment));
        when(shipmentRepositoryMock.save(Mockito.any())).thenReturn(shipment);
        when(applicationContextMock.getCurrentUser()).thenReturn(new User());

        testService.createShipment(createShipmentRequest);

        verify(shipmentRepositoryMock, times(1)).save(Mockito.any());
        verify(shipmentStatusHistoryRepositoryMock, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Verify getShipment should be success")
    void test2() {
        var shipment = new Shipment();
        shipment.setTruck(new Truck());
        shipment.setDevice(new Device());
        shipment.setDriver(new Driver());
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(shipment));
        when(shipmentStatusHistoryRepositoryMock.findFirst1ByShipmentOrderByInsertedDateDesc(Mockito.any())).thenReturn(new ShipmentStatusHistory());

        var result = testService.getShipment(UUID.randomUUID());

        assertTrue(result != null);
    }

    @Test
    @DisplayName("Verify getShipment should be thrown ShipmentNotFoundException")
    void test3() {
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        assertThrows(ShipmentNotFoundException.class, () -> testService.getShipment(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Verify updateStatus should be success")
    void test4() {
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.of(new Shipment()));
        when(applicationContextMock.getCurrentUser()).thenReturn(new User());

        testService.updateStatus(UUID.randomUUID(), ShipmentStatus.HeadingToDestination);

        verify(shipmentStatusHistoryRepositoryMock, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Verify updateStatus should be thrown ShipmentNotFoundException")
    void test5() {
        when(shipmentRepositoryMock.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        assertThrows(ShipmentNotFoundException.class, () -> testService.updateStatus(UUID.randomUUID(), ShipmentStatus.HeadingToDestination));
    }
}
