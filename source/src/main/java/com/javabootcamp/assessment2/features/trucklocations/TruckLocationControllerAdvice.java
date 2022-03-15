package com.javabootcamp.assessment2.features.trucklocations;

import com.javabootcamp.assessment2.views.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TruckLocationControllerAdvice {

    @ExceptionHandler(SaveTruckLocationFailureException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse saveFailure(SaveTruckLocationFailureException e) {
        return new ErrorResponse(e.getMessage());
    }

}
