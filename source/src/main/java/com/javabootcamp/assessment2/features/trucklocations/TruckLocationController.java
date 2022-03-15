package com.javabootcamp.assessment2.features.trucklocations;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure/trucklocations")
public class TruckLocationController extends SecuredRestController {

    @Autowired
    private TruckLocationService truckLocationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TruckLocationResponse saveTruckLocation(@RequestBody TruckLocationRequest truckLocationRequest) {
        TruckLocationResponse truckLocationResponse = truckLocationService.SaveTruckLocation(truckLocationRequest);
        return truckLocationResponse;
    }
}
