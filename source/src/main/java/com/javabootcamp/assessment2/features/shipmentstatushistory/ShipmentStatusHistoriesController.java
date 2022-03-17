package com.javabootcamp.assessment2.features.shipmentstatushistory;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import com.javabootcamp.assessment2.enums.ShipmentStatus;
import com.javabootcamp.assessment2.features.shipment.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/secure/shipmentstatushistories")
public class ShipmentStatusHistoriesController extends SecuredRestController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void updateStatus(@RequestParam UUID shipmentId, @RequestParam ShipmentStatus status) {
        shipmentService.updateStatus(shipmentId, status);
    }

}
