package br.com.itau.tailor.exception.model;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String code;
    private String message;
    private Object details;
    private LocalDateTime timestamp;
    private String path;
    private String traceId;

    public ErrorResponse(String code, String message, Object details) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public Object getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getTraceId() { return traceId; }
    public void setTraceId(String traceId) { this.traceId = traceId; }
}