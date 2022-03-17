package com.javabootcamp.assessment2.features.dashboard;

import com.javabootcamp.assessment2.features.shipment.ShipmentNotFoundException;
import com.javabootcamp.assessment2.features.shipment.ShipmentTypeInvalidException;
import com.javabootcamp.assessment2.views.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DashboardControllerAdvice {
    @ExceptionHandler(ShipmentNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse shipmentNotFound(ShipmentNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ShipmentTypeInvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse shipmentTypeInvalid(ShipmentTypeInvalidException e) {
        return new ErrorResponse(e.getMessage());
    }
}
