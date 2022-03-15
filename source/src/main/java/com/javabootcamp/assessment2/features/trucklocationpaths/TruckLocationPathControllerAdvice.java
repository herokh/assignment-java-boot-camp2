package com.javabootcamp.assessment2.features.trucklocationpaths;

import com.javabootcamp.assessment2.views.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TruckLocationPathControllerAdvice {

    @ExceptionHandler(SaveTruckLocationPathFailureException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse saveFailure(SaveTruckLocationPathFailureException e) {
        return new ErrorResponse(e.getMessage());
    }

}
