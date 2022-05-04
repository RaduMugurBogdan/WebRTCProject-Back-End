package com.example.AccountAPI.util;

import com.example.AccountAPI.dto.input_dtos.UserLoginDto;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;


/*

Password format:

Password must contain at least one digit [0-9].
Password must contain at least one lowercase Latin character [a-z].
Password must contain at least one uppercase Latin character [A-Z].
Password must contain at least one special character like ! @ # & ( ).
Password must contain a length of at least 8 characters and a maximum of 20 characters.



Username format:

The username length must range between 7 to 20 otherwise, it’s an invalid username.
The username is allowed to contain only underscores ( _ ) other than alphanumeric characters.
The first character of the username must be an alphabetic character, i.e., [a-z] or [A-Z].

 */

@Component
public class AccountDataValidator {

    @Autowired
    private UserRepository userRepository;

    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
    private final Pattern usernamePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{6,19}$");
    private final Pattern namePattern = Pattern.compile("^[A-Z]{1}[a-z]+$");

    private final String EMPTY_FIELD_ERROR = "The value can not be null.";


    private final String UNKNOWN_USERNAME_ERROR = "Unknown username.";
    private final String INVALID_EMAIL_ERROR = "The value is not an email address.";
    private final String EMAIL_ALREADY_IN_USE_ERROR = "The email is already in use.";

    private final String INVALID_USERNAME_ERROR = "Username must contain between 7 and 20 alphanumeric characters(and '_' sign )";
    private final String USERNAME_ALREADY_IN_USE_ERROR = "The username is already in use.";

    private final String INVALID_NAME_ERROR = "The name must be one word and start with an uppercase character.";

    private final String PASSWORD_FORMAT_ERROR = "Password must contain between 8 and 20 alphanumeric and sign (!,@,#,&,(,)) characters.";


    private boolean isValidUsername(String username) {
        return usernamePattern.matcher(username).matches();
    }

    private boolean isValidEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return passwordPattern.matcher(password).matches();
    }

    private final boolean isEmptyString(String input) {
        return (input == null) || (input.isEmpty());
    }

    private final boolean isValidName(String name) {
        return Character.isUpperCase(name.charAt(0)) && namePattern.matcher(name).matches();
    }

    public Optional<AccountCreationFailureDetails> checkUserDataValidity(UserModel input) {
        AccountCreationFailureDetails errorResponse = new AccountCreationFailureDetails();

        if (isEmptyString(input.getEmail())) {
            errorResponse.setEmailError(EMPTY_FIELD_ERROR);
        }
        if (isEmptyString(input.getFirstName())) {
            errorResponse.setFirstNameError(EMPTY_FIELD_ERROR);
        }
        if (isEmptyString(input.getLastName())) {
            errorResponse.setLastNameError(EMPTY_FIELD_ERROR);
        }
        if (isEmptyString(input.getPassword())) {
            errorResponse.setPasswordError(EMPTY_FIELD_ERROR);
        }
        if (isEmptyString(input.getUsername())) {
            errorResponse.setUsernameError(EMPTY_FIELD_ERROR);
        }

        if (errorResponse.getFirstNameError() == null) {
            if (!isValidName(input.getFirstName())) {
                errorResponse.setFirstNameError(INVALID_NAME_ERROR);
            }
        }

        if (errorResponse.getLastNameError() == null) {
            if (!isValidName(input.getLastName())) {
                errorResponse.setLastNameError(INVALID_NAME_ERROR);
            }
        }

        if (errorResponse.getEmailError() == null) {
            if (!isValidEmail(input.getEmail())) {
                errorResponse.setEmailError(INVALID_EMAIL_ERROR);
            }
        }

        if (errorResponse.getPasswordError() == null) {
            if (!isValidPassword(input.getPassword())) {
                errorResponse.setPasswordError(PASSWORD_FORMAT_ERROR);
            }
        }

        if (errorResponse.getUsernameError() == null) {
            if (!isValidUsername(input.getUsername())) {
                errorResponse.setUsernameError(INVALID_USERNAME_ERROR);
            }
        }


        if (errorResponse.getUsernameError() == null) {
            if (userRepository.findByUsername(input.getUsername())) {
                errorResponse.setUsernameError(USERNAME_ALREADY_IN_USE_ERROR);
            }
        }

        if (errorResponse.getEmailError() == null) {
            if (userRepository.findByEmail(input.getEmail())) {
                errorResponse.setEmailError(EMAIL_ALREADY_IN_USE_ERROR);
            }
        }

        if (errorResponse.isErrorFound()) {
            return Optional.of(errorResponse);
        }
        return Optional.empty();
    }

    public Optional<LoginFailureDetails> checkUserLoginDataValidity(UserLoginDto input) {
        LoginFailureDetails errorResponse = new LoginFailureDetails();

        if (isEmptyString(input.getUsername())) {
            errorResponse.setUsernameError(EMPTY_FIELD_ERROR);
        }
        if (errorResponse.getUsernameError() == null && !isValidUsername(input.getUsername())) {
            errorResponse.setUsernameError(INVALID_USERNAME_ERROR);
        }
        if (isEmptyString(input.getPassword())) {
            errorResponse.setPasswordError(EMPTY_FIELD_ERROR);
        }
        if (errorResponse.getPasswordError() == null && !userRepository.findByUsername(input.getUsername())) {
            errorResponse.setUsernameError(UNKNOWN_USERNAME_ERROR);
        }

        if (errorResponse.isErrorFound()) {
            return Optional.of(errorResponse);
        }
        return Optional.empty();
    }

}
