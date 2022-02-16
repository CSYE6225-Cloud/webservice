package com.chengyan.webapp.ExceptionController;

public class UndesiredParameterException extends RuntimeException {
    public UndesiredParameterException(String undesiredParameter) {
        super("Undesired Parameter: " + undesiredParameter);
    }
}
