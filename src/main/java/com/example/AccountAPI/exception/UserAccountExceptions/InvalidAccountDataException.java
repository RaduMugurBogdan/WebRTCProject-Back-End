package com.example.AccountAPI.exception.UserAccountExceptions;

import com.example.AccountAPI.util.AccountCreationFailureDetails;

public class InvalidAccountDataException extends RuntimeException{
    private AccountCreationFailureDetails errorDetails;
    public InvalidAccountDataException(AccountCreationFailureDetails errorDetails){
        super();
        this.errorDetails=errorDetails;
    }
    public AccountCreationFailureDetails getErrorDetails(){
        return errorDetails;
    }
}
