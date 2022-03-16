package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.exception.UserAccountExceptions.EmailAlreadyInUseException;
import com.example.AccountAPI.exception.UserAccountExceptions.UsernameAlreadyInUseException;
import com.example.AccountAPI.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {
    Optional<UUID> createUser(UserModel user);
    List<UserModel> getAllUsers();
}
