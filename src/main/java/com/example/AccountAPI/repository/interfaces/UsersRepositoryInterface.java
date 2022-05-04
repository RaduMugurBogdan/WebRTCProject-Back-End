package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepositoryInterface extends BasicRepository<UserModel> {
    boolean findByEmail(String email);

    boolean findByUsername(String username);

    Optional<UserModel> getByUsername(String username);
    Optional<PublicUserModel> getPublicUserModelById(UUID userId);
    List<PublicUserModel> getUsersLike(String like);
}
