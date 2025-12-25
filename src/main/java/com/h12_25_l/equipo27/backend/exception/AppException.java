package com.h12_25_l.equipo27.backend.exception;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {

    private final String errorCode;

    protected AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    protected AppException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}

