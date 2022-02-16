package com.chengyan.webapp.ExceptionController;

public class UserExistedException extends RuntimeException {
    public UserExistedException(String username) {
        super("User: " + username + " existed.");
    }
}
