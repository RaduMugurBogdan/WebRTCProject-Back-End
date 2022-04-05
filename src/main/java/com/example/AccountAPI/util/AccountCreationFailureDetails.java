package com.example.AccountAPI.util;

public class AccountCreationFailureDetails {

    private String firstNameError;
    private String lastNameError;
    private String emailError;
    private String usernameError;
    private String passwordError;

    private boolean errorFound=false;

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String email) {
        errorFound=true;
        this.emailError = email;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(String firstNameError) {
        errorFound=true;
        this.firstNameError = firstNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        errorFound=true;
        this.lastNameError = lastNameError;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        errorFound=true;
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        errorFound=true;
        this.passwordError = passwordError;
    }

    public boolean isErrorFound(){
        return errorFound;
    }
}
