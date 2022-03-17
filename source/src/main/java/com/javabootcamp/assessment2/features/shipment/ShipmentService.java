package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.entities.*;
import com.javabootcamp.assessment2.enums.ShipmentStatus;
import com.javabootcamp.assessment2.features.branch.BranchNotFoundException;
import com.javabootcamp.assessment2.features.branch.BranchRepository;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterNotFoundException;
import com.javabootcamp.assessment2.features.cashcenter.CashCenterRepository;
import com.javabootcamp.assessment2.features.device.DeviceNotFoundException;
import com.javabootcamp.assessment2.features.device.DeviceRepository;
import com.javabootcamp.assessment2.features.driver.DriverNotFoundException;
import com.javabootcamp.assessment2.features.driver.DriverRepository;
import com.javabootcamp.assessment2.features.shipmentstatushistory.ShipmentStatusHistoryRepository;
import com.javabootcamp.assessment2.features.truck.TruckNotFoundException;
import com.javabootcamp.assessment2.features.truck.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShipmentService {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private TruckRepository truckRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private CashCenterRepository cashCenterRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private ShipmentStatusHistoryRepository shipmentStatusHistoryRepository;
    @Autowired
    private ApplicationContext applicationContext;

    public ShipmentService(DriverRepository driverRepository, DeviceRepository deviceRepository, TruckRepository truckRepository, BranchRepository branchRepository, CashCenterRepository cashCenterRepository, ShipmentRepository shipmentRepository, ShipmentStatusHistoryRepository shipmentStatusHistoryRepository, ApplicationContext applicationContext) {
        this.driverRepository = driverRepository;
        this.deviceRepository = deviceRepository;
        this.truckRepository = truckRepository;
        this.branchRepository = branchRepository;
        this.cashCenterRepository = cashCenterRepository;
        this.shipmentRepository = shipmentRepository;
        this.shipmentStatusHistoryRepository = shipmentStatusHistoryRepository;
        this.applicationContext = applicationContext;
    }

    public ShipmentService(){}

    @Transactional
    public UUID createShipment(CreateShipmentRequest createShipmentRequest){
        Shipment shipment = new Shipment();

        setSenderReceiverShipment(createShipmentRequest, shipment);

        var driver = driverRepository.findById(createShipmentRequest.getDriverId())
                .orElseThrow(() -> new DriverNotFoundException("Driver not found."));
        var truck = truckRepository.findById(createShipmentRequest.getTruckId())
                .orElseThrow(() -> new TruckNotFoundException("Truck not found."));
        var device = deviceRepository.findById(createShipmentRequest.getDeviceId())
                .orElseThrow(() -> new DeviceNotFoundException("Device not found."));

        shipment.setDriver(driver);
        shipment.setTruck(truck);
        shipment.setDevice(device);

        Date now = new Date();
        User currentUser = applicationContext.getCurrentUser();
        shipment.setInsertedDate(now);
        shipment.setInsertedBy(currentUser.getId());

        var result = shipmentRepository.save(shipment);
        updateStatus(shipment.getId(), ShipmentStatus.Prepare);

        return result.getId();
    }

    private void setSenderReceiverShipment(CreateShipmentRequest createShipmentRequest, Shipment shipment) {
        shipment.setSenderId(createShipmentRequest.getSenderId());
        shipment.setReceiverId(createShipmentRequest.getReceiverId());
        shipment.setShipmentType(createShipmentRequest.getShipmentType());

        Branch branch = null;
        CashCenter cashCenter = null;

        switch (createShipmentRequest.getShipmentType()) {
            case BranchToCashCenter:
                branch = branchRepository.findById(createShipmentRequest.getSenderId())
                        .orElseThrow(() -> new BranchNotFoundException("Branch not found."));
                cashCenter = cashCenterRepository.findById(createShipmentRequest.getReceiverId())
                        .orElseThrow(() -> new CashCenterNotFoundException("Cash center not found."));

                shipment.setOriginLat(branch.getAddress().getLatitude());
                shipment.setOriginLng(branch.getAddress().getLongitude());
                shipment.setDestinationLat(cashCenter.getAddress().getLatitude());
                shipment.setDestinationLng(cashCenter.getAddress().getLongitude());
                break;
            case CashCenterToBranch:
                branch = branchRepository.findById(createShipmentRequest.getReceiverId())
                        .orElseThrow(() -> new BranchNotFoundException("Branch not found."));
                cashCenter = cashCenterRepository.findById(createShipmentRequest.getSenderId())
                        .orElseThrow(() -> new CashCenterNotFoundException("Cash center not found."));

                shipment.setOriginLat(cashCenter.getAddress().getLatitude());
                shipment.setOriginLng(cashCenter.getAddress().getLongitude());
                shipment.setDestinationLat(branch.getAddress().getLatitude());
                shipment.setDestinationLng(branch.getAddress().getLongitude());
                break;
            default:
                throw new ShipmentTypeInvalidException("Shipment type is invalid.");
        }
    }

    public ShipmentFullResponse getShipment(UUID id){
        var shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found."));

        var response = new ShipmentFullResponse();
        response.setId(shipment.getId());
        response.setTruckId(shipment.getTruck().getId());
        response.setDeviceId(shipment.getDevice().getId());
        response.setDriverId(shipment.getDriver().getId());

        response.setSenderId(shipment.getSenderId());
        response.setReceiverId(shipment.getReceiverId());
        response.setShipmentType(shipment.getShipmentType());

        response.setOriginLat(shipment.getOriginLat());
        response.setOriginLng(shipment.getOriginLng());

        response.setDestinationLat(shipment.getDestinationLat());
        response.setDestinationLng(shipment.getDestinationLng());

        response.setInserted(shipment.getInsertedDate());
        response.setInsertedBy(shipment.getInsertedBy());

        ShipmentStatusHistory currentStatus = shipmentStatusHistoryRepository.findFirst1ByShipmentOrderByInsertedDateDesc(shipment);
        response.setStatus(currentStatus.getStatus());

        return response;
    }

    public ShipmentListResponse getShipmentList(Date insertedDate){
        List<Shipment> shipments = shipmentRepository.findAllByInsertedDate(insertedDate);

        ShipmentListResponse response = new ShipmentListResponse();
        var items = new ArrayList<ShipmentItemResponse>();

        for (var shipment:shipments) {
            var item = new ShipmentItemResponse();

            item.setId(shipment.getId());
            item.setSenderId(shipment.getSenderId());
            item.setReceiverId(shipment.getReceiverId());
            item.setShipmentType(shipment.getShipmentType());

            item.setInserted(shipment.getInsertedDate());
            item.setInsertedBy(shipment.getInsertedBy());

            items.add(item);
        }

        response.setResult(items);

        return response;
    }

    public void updateStatus(UUID id, ShipmentStatus newStatus) {
        var shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found."));

        var shipmentStatusHistory = new ShipmentStatusHistory();
        shipmentStatusHistory.setStatus(newStatus);
        shipmentStatusHistory.setShipment(shipment);
        shipmentStatusHistory.setInsertedDate(new Date());
        var currentUser = applicationContext.getCurrentUser();
        shipmentStatusHistory.setInsertedBy(currentUser.getId());

        shipmentStatusHistoryRepository.save(shipmentStatusHistory);
    }

}
