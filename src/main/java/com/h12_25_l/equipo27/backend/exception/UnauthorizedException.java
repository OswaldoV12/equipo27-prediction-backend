package com.h12_25_l.equipo27.backend.exception;

public class UnauthorizedException extends AppException {
    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED");
    }
}
