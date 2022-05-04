package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserServiceInterface {
    Optional<UUID> createUser(UserModel user);

    List<UserModel> getAllUsers();

    Optional<UserModel> getByUsername(String username);

    List<PublicUserModel> getUsersLike(String like);

}
