package com.h12_25_l.equipo27.backend.exception;

// Error de recurso no encontrado (HTTP 404)
public class NotFoundException extends AppException {
    public NotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}

