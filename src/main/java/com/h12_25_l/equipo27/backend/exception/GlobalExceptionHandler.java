package com.h12_25_l.equipo27.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejo de errores de validación de negocio
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionDetails> handleValidationException(
            ValidationException ex, HttpServletRequest request) {

        LOG.warn("Validación fallida: {}", ex.getMessage());

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrorCode(),
                ex.getMessage(),
                "WARN",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(details);
    }

    // Manejo de errores de servicios externos (HTTP 502)
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ExceptionDetails> handleExternalServiceException(
            ExternalServiceException ex, HttpServletRequest request) {

        LOG.error("Error en servicio externo: {}", ex.getMessage(), ex);

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_GATEWAY.value(),
                ex.getErrorCode(),
                ex.getMessage(),
                "ERROR",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(details);
    }

    // Manejo de recursos no encontrados (HTTP 404)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleNotFoundException(
            NotFoundException ex, HttpServletRequest request) {

        LOG.warn("Recurso no encontrado: {}", ex.getMessage());

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getErrorCode(),
                ex.getMessage(),
                "WARN",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
    }

    // Manejo de errores inesperados y RuntimeExceptions (HTTP 500)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionDetails> handleGeneralException(
            Throwable ex, HttpServletRequest request) {

        LOG.error("Error inesperado: {}", ex.getMessage(), ex);

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                "Ha ocurrido un error inesperado. Por favor, contacte al soporte.",
                "ERROR",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
    }

    // Manejo de errores de validación de DTOs (@Valid de Spring)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleDTOValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Concatenar todos los errores de validación en un solo mensaje
        String userMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        LOG.warn("Errores de validación DTO: {}", userMessage);

        ExceptionDetails details = new ExceptionDetails(
                HttpStatus.BAD_REQUEST.value(),
                "DTO_VALIDATION_ERROR",
                userMessage,
                "WARN",
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(details);
    }



}


