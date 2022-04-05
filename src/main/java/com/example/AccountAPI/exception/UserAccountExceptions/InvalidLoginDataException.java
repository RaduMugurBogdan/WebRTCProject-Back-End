package com.example.AccountAPI.exception.UserAccountExceptions;

import com.example.AccountAPI.util.LoginFailureDetails;

public class InvalidLoginDataException extends RuntimeException{
    private LoginFailureDetails loginFailureDetails;
    public InvalidLoginDataException(LoginFailureDetails loginFailureDetails){
        super();
        this.loginFailureDetails=loginFailureDetails;
    }
    public LoginFailureDetails getLoginFailureDetails(){
        return this.loginFailureDetails;
    }
}
