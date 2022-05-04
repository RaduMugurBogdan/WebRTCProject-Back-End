package com.example.AccountAPI.util;


public interface JwtUtilsInterface {
    String generateToken(String username);

    String validateTokenAndRetrieveSubject(String token);
}
