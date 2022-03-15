package com.javabootcamp.assessment2.features.trucklocationpaths;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure/trucklocationpaths")
public class TruckLocationPathsController extends SecuredRestController {

    @Autowired
    private TruckLocationPathService truckLocationPathService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TruckLocationPathResponse saveTruckLocation(@RequestBody TruckLocationPathRequest truckLocationPathRequest) {
        TruckLocationPathResponse truckLocationPathResponse = truckLocationPathService.saveTruckLocationPath(truckLocationPathRequest);
        return truckLocationPathResponse;
    }
}
