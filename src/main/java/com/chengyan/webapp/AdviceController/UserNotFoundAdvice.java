package com.chengyan.webapp.AdviceController;

import com.chengyan.webapp.ExceptionController.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class UserNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void userNotFoundHandler(UserNotFoundException ex) {
        // TODO: log?
        return;
    }
}
