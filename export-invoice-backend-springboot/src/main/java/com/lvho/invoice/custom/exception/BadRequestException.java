package com.lvho.invoice.custom.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    HttpStatus status;
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public BadRequestException(String message) {
        super(message);
    }
}