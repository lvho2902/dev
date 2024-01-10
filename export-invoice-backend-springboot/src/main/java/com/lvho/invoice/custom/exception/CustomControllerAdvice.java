package com.lvho.invoice.custom.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lvho.invoice.data.response.ErrorResponse;

@ControllerAdvice
class CustomControllerAdvice {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCustomParameterConstraintExceptions(BadRequestException e) {
        return new ErrorResponse(e.getMessage());
    }
}
