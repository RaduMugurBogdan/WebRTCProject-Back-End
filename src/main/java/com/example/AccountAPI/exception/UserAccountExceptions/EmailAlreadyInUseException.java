package com.example.AccountAPI.exception.UserAccountExceptions;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException() {
        super("Email already in use.");
    }

    public EmailAlreadyInUseException(String email) {
        super("The email address <" + email + "> is already in use.");
    }
}
