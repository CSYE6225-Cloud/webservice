package com.chengyan.webapp.AdviceController;

import com.chengyan.webapp.ExceptionController.UndesiredParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class UndesiredParameterAdvice {
    @ResponseBody
    @ExceptionHandler(UndesiredParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void undesiredParameterHandler(UndesiredParameterException ex) {
    }
}
