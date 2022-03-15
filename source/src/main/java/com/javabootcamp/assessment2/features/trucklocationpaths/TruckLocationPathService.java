package com.javabootcamp.assessment2.features.trucklocationpaths;

import com.javabootcamp.assessment2.entities.TruckLocationPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckLocationPathService {

    @Autowired
    private TruckLocationPathRepository truckLocationPathRepository;

    public TruckLocationPathResponse saveTruckLocationPath(TruckLocationPathRequest truckLocationPathRequest) {
        try {
            // TODO: Get shipment by device id.

            var entity = new TruckLocationPath(truckLocationPathRequest.getLatitude(), truckLocationPathRequest.getLongitude());
            truckLocationPathRepository.save(entity);
            return new TruckLocationPathResponse(true);
        }
        catch (RuntimeException e) {
            throw new SaveTruckLocationPathFailureException("Save truck location fail.");
        }
    }

}
