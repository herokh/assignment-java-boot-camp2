package com.javabootcamp.assessment2.features.trucklocations;

import com.javabootcamp.assessment2.entities.TruckLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckLocationService {

    @Autowired
    private TruckLocationRepository truckLocationRepository;

    public TruckLocationResponse SaveTruckLocation(TruckLocationRequest truckLocationRequest) {
        try {
            var entity = new TruckLocation(truckLocationRequest.getLatitude(), truckLocationRequest.getLongitude());
            truckLocationRepository.save(entity);
            return new TruckLocationResponse(true);
        }
        catch (RuntimeException e) {
            throw new SaveTruckLocationFailureException("Save truck location fail.");
        }
    }

}
