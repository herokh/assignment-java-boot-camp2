package com.javabootcamp.assessment2.features.trucklocationpath;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.TruckLocationPath;
import com.javabootcamp.assessment2.features.shipment.ShipmentNotFoundException;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckLocationPathService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private TruckLocationPathRepository truckLocationPathRepository;

    public TruckLocationPathService(ShipmentRepository shipmentRepository, TruckLocationPathRepository truckLocationPathRepository) {
        this.shipmentRepository = shipmentRepository;
        this.truckLocationPathRepository = truckLocationPathRepository;
    }

    public TruckLocationPathService(){}

    public TruckLocationPathResponse saveTruckLocationPath(CreateTruckLocationPathRequest createTruckLocationPathRequest) {
        try {
            Shipment shipment = shipmentRepository.findById(createTruckLocationPathRequest.getShipmentId())
                    .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found"));
            var entity = new TruckLocationPath(
                    shipment,
                    createTruckLocationPathRequest.getLatitude(),
                    createTruckLocationPathRequest.getLongitude());
            truckLocationPathRepository.save(entity);
            var response = new TruckLocationPathResponse();
            response.setSuccess(true);
            return response;
        }
        catch (RuntimeException e) {
            throw new SaveTruckLocationPathFailureException("Save truck location fail.");
        }
    }

}
