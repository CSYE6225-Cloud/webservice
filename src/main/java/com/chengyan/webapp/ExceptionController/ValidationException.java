package com.chengyan.webapp.ExceptionController;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
