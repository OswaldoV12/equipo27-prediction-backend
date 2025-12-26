package com.h12_25_l.equipo27.backend.exception;

import java.time.LocalDateTime;

public class ExceptionDetails {

    private LocalDateTime timestamp;
    private int status;
    private String errorCode;
    private String userMessage;
    private String severity;
    private String path;

    public ExceptionDetails(int status, String errorCode, String userMessage, String severity, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.errorCode = errorCode;
        this.userMessage = userMessage;
        this.severity = severity;
        this.path = path;
    }

    // Getters y setters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getErrorCode() { return errorCode; }
    public String getUserMessage() { return userMessage; }
    public String getSeverity() { return severity; }
    public String getPath() { return path; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setStatus(int status) { this.status = status; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    public void setSeverity(String severity) { this.severity = severity; }
    public void setPath(String path) { this.path = path; }
}


