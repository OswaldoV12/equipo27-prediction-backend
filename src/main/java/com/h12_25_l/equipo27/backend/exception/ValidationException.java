package com.h12_25_l.equipo27.backend.exception;

// Error por validación de datos del usuario (HTTP 400)
public class ValidationException extends AppException {
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR");
    }
}

