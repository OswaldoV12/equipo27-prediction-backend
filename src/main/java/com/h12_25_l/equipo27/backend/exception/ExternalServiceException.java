package com.h12_25_l.equipo27.backend.exception;

// Error de servicios externos (por ejemplo DS API) (HTTP 502)
public class ExternalServiceException extends AppException {
    public ExternalServiceException(String message, Throwable cause) {
        super(message, "EXTERNAL_SERVICE_FAIL", cause);
    }

    public ExternalServiceException(String message) {
        super(message, "EXTERNAL_SERVICE_FAIL");
    }
}

