package com.javabootcamp.assessment2.features.shipment;

import com.javabootcamp.assessment2.controllers.SecuredRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/secure/shipments")
public class ShipmentsController extends SecuredRestController {

    @Autowired
    private ShipmentService shipmentService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createShipment(@RequestBody CreateShipmentRequest createShipmentRequest) {
        return shipmentService.createShipment(createShipmentRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShipmentFullResponse getShipment(@PathVariable UUID id) {
        return shipmentService.getShipment(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ShipmentListResponse getShipmentList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date insertedDate) {
        return shipmentService.getShipmentList(insertedDate);
    }
}
