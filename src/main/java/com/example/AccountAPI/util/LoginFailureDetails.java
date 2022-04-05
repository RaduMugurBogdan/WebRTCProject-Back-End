package com.example.AccountAPI.util;

public class LoginFailureDetails {
    private String usernameError;
    private String passwordError;
    private boolean errorFound=false;


    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.errorFound=true;
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.errorFound=true;
        this.passwordError = passwordError;
    }

    public boolean isErrorFound() {
        return errorFound;
    }

    public void setErrorFound(boolean errorFound) {
        this.errorFound = errorFound;
    }
}
