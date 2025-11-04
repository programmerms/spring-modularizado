package com.tailor.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseException extends RuntimeException {

    private final String errorCode;

    protected BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public Map<String, Object> getErrorContext() {
        StackTraceElement element = this.getStackTrace()[0];

        Map<String, Object> context = new LinkedHashMap<>();
        context.put("className", element.getClassName());
        context.put("methodName", element.getMethodName());
        context.put("line", element.getLineNumber());
        context.put("errorCode", this.getErrorCode());
        context.put("message", this.getMessage());
        context.put("exceptionType", this.getClass().getSimpleName());

        return context;
    }
}
