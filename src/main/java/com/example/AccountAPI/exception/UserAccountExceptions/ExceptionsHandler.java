package com.example.AccountAPI.exception.UserAccountExceptions;

import com.example.AccountAPI.util.LoginFailureDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({InvalidAccountDataException.class})
    public ResponseEntity<Object> handleAccountDataConstraintViolation(InvalidAccountDataException ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getErrorDetails(), new HttpHeaders(), 400);
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<Object> handleLoginPasswordValidity(BadCredentialsException ex, WebRequest request) {
        LoginFailureDetails loginFailureDetails = new LoginFailureDetails();
        loginFailureDetails.setPasswordError("Wrong password.");
        return new ResponseEntity<Object>(loginFailureDetails, new HttpHeaders(), 403);
    }

    @ExceptionHandler({InvalidLoginDataException.class})
    public ResponseEntity<Object> handleLoginDataConstraintViolation(InvalidLoginDataException ex, WebRequest request) {
        return new ResponseEntity<Object>(ex.getLoginFailureDetails(), new HttpHeaders(), 403);
    }
}
