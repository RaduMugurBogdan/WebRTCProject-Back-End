package com.example.AccountAPI.exception.UserAccountExceptions;

public class UsernameAlreadyInUseException extends Exception {
    public UsernameAlreadyInUseException() {
        super("Username already in use.");
    }

    public UsernameAlreadyInUseException(String username) {
        super("The username <" + username + "> is already in use.");
    }
}
