package com.lvho.invoice.custom.exception;

import org.springframework.http.HttpStatus;

public class CustomParameterConstraintException extends RuntimeException {
    HttpStatus status;
    public CustomParameterConstraintException() {
        super();
    }

    public CustomParameterConstraintException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}