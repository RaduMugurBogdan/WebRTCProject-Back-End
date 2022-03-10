package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendsRepositoryInterface {
    List<PublicUserModel> getAllFriends(UUID userId);

    UUID create(UUID currentUserId, UUID friendUserId);

    boolean exists(UUID currentUserId, UUID friendUserId);

    void delete(UUID currentUserId, UUID friendUserId);
}
