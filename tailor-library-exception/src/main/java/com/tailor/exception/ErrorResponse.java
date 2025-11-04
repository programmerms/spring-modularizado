
package com.tailor.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String errorCode;
    private String exceptionClass;
    private String originClass;
    private String originMethod;
    private Map<String,String> validationErrors;

    public ErrorResponse(LocalDateTime timestamp,String message,String errorCode,
                         String exceptionClass,String originClass,String originMethod,
                         Map<String,String> validationErrors) {
        this.timestamp=timestamp;
        this.message=message;
        this.errorCode=errorCode;
        this.exceptionClass=exceptionClass;
        this.originClass=originClass;
        this.originMethod=originMethod;
        this.validationErrors=validationErrors;
    }
}
