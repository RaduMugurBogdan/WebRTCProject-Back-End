package com.example.AccountAPI.exception.UserAccountExceptions;

import com.example.AccountAPI.util.AccountCreationFailureDetails;

public class InvalidInputException extends RuntimeException{
    private AccountCreationFailureDetails errorDetails;
    public InvalidInputException(AccountCreationFailureDetails errorDetails){
        super();
        this.errorDetails=errorDetails;
    }
    public AccountCreationFailureDetails getErrorDetails(){
        return errorDetails;
    }
}
