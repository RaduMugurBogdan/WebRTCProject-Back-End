package com.example.AccountAPI.exception.UserAccountExceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ InvalidInputException.class })
    public ResponseEntity<Object> handleConstraintViolation(InvalidInputException ex, WebRequest request) {
       return new ResponseEntity<Object>(ex.getErrorDetails(),new HttpHeaders(),400);
    }
}
