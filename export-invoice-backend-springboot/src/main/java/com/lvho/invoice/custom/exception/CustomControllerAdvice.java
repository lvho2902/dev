package com.lvho.invoice.custom.exception;
import com.lvho.invoice.custom.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(CustomParameterConstraintException.class)
    public ResponseEntity<ErrorResponse> handleCustomParameterConstraintExceptions(Exception e) {

        CustomParameterConstraintException exception = (CustomParameterConstraintException)e;
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), exception.status);
    }
}
