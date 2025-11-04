package com.tailor.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final String appName;

    public GlobalExceptionHandler(AppInfoProvider appInfoProvider) {
        this.appName = appInfoProvider.getAppName();
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        log.error("[{}] Tailor Error -> {}", appName, ex.getErrorContext(), ex);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getErrorContext());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();
        errors.put("errorCode", "VALIDATION_ERROR");
        errors.put("exceptionType", "ValidationException");

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errors.put("message", "Validation failed");
        errors.put("fields", fieldErrors);

        log.warn("[{}] Validation Error -> {}", appName, errors);

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        StackTraceElement element = ex.getStackTrace()[0];

        Map<String, Object> body = new HashMap<>();
        body.put("className", element.getClassName());
        body.put("methodName", element.getMethodName());
        body.put("line", element.getLineNumber());
        body.put("errorCode", "GENERIC_ERROR");
        body.put("message", ex.getMessage());
        body.put("exceptionType", ex.getClass().getSimpleName());

        log.error("[{}] Unknown Error -> {}", appName, body, ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
