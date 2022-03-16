package com.javabootcamp.assessment2.features.trucklocationpaths;

import com.javabootcamp.assessment2.entities.Shipment;
import com.javabootcamp.assessment2.entities.TruckLocationPath;
import com.javabootcamp.assessment2.features.shipment.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckLocationPathService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private TruckLocationPathRepository truckLocationPathRepository;

    public TruckLocationPathResponse saveTruckLocationPath(CreateTruckLocationPathRequest createTruckLocationPathRequest) {
        try {
            Shipment shipment = shipmentRepository.findByDeviceId(createTruckLocationPathRequest.getDeviceId());
            var entity = new TruckLocationPath(
                    shipment.getId(),
                    createTruckLocationPathRequest.getLatitude(),
                    createTruckLocationPathRequest.getLongitude());
            truckLocationPathRepository.save(entity);
            return new TruckLocationPathResponse(true);
        }
        catch (RuntimeException e) {
            throw new SaveTruckLocationPathFailureException("Save truck location fail.");
        }
    }

}
