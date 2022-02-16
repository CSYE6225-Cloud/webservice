package com.chengyan.webapp.AdviceController;

import com.chengyan.webapp.ExceptionController.UserExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestControllerAdvice
public class UserExistedAdvice {

    @ExceptionHandler(UserExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void userExistedHandler(UserExistedException ex) {
    }
}
