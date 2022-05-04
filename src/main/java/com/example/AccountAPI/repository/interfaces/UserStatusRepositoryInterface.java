package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.UserStatusModel;

import java.util.Optional;
import java.util.UUID;

public interface UserStatusRepositoryInterface {
    Optional<UserStatusModel> createUserStatus(UserStatusModel userStatus);
    Optional<UserStatusModel> getUserStatus(UUID userID);
    void updateUserStatus(UserStatusModel userStatus);
    void deleteUserStatus(UUID userId);
}
