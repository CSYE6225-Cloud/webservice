package com.chengyan.webapp.ExceptionController;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User: " + username + " not found.");
    }
}
