package com.example.testhtml.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WordPhoneExpHandler {

    @ExceptionHandler(WorldPhoneExp.class)
    @ResponseBody
    ResponseEntity<?> worldPhoneNotFound(WorldPhoneExp e){
        return new ResponseEntity(e.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
