package com.example.AccountAPI.util;

public class AccountCreationFailureDetails {

    private String fistNameError;
    private String lastNameError;
    private String emailError;
    private String usernameError;
    private String passwordError;
    private String passwordConfirmationError;

    private boolean errorFound=false;

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String email) {
        this.emailError = email;
    }

    public String getFistNameError() {
        return fistNameError;
    }

    public void setFistNameError(String fistNameError) {
        errorFound=true;
        this.fistNameError = fistNameError;
    }

    public String getLastNameError() {
        errorFound=true;
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        errorFound=true;
        this.lastNameError = lastNameError;
    }

    public String getUsernameError() {
        errorFound=true;
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        errorFound=true;
        this.usernameError = usernameError;
    }

    public String getPasswordError() {
        errorFound=true;
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        errorFound=true;
        this.passwordError = passwordError;
    }

    public String getPasswordConfirmationError() {
        return passwordConfirmationError;
    }

    public void setPasswordConfirmationError(String passwordConfirmationError) {
        this.passwordConfirmationError = passwordConfirmationError;
    }
    public boolean isErrorFound(){
        return errorFound;
    }
}
