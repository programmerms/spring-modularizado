package br.com.itau.tailor.exception.handler;

import br.com.itau.tailor.exception.exception.BusinessException;
import br.com.itau.tailor.exception.exception.NotFoundException;
import br.com.itau.tailor.exception.model.ErrorResponse;
import br.com.itau.tailor.exception.util.ApiNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ApiNameResolver apiNameResolver;

    public GlobalExceptionHandler(ApiNameResolver apiNameResolver) {
        this.apiNameResolver = apiNameResolver;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> err.getField(),
                        err -> err.getDefaultMessage(),
                        (existing, replacement) -> existing));

        String api = apiNameResolver.resolve();
        log.warn("Validation error in API [{}]: {}", api, errors);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", "Field validation error", errors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        String api = apiNameResolver.resolve();
        log.info("Business error in API [{}]: [{}] {}", api, ex.getCode(), ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(ex.getCode(), ex.getMessage(), null));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        String api = apiNameResolver.resolve();
        log.info("Resource not found in API [{}]: {}", api, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getCode(), ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        String api = apiNameResolver.resolve();
        // Log a summarized message containing API name and exception message (no stacktrace)
        log.error("Unexpected error in API [{}]: {}", api, ex.getMessage());
        ErrorResponse response = new ErrorResponse(
                "INTERNAL_ERROR",
                ex.getMessage() != null ? ex.getMessage() : "Internal server error",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}