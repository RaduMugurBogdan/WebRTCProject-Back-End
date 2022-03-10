package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendsServiceInterface {
    List<PublicUserModel> getAllFriends(UUID userId);
    Optional<UUID> createFriendRelation(UUID currentUserId, UUID friendUserId);
    void deleteFriendRelation(UUID currentUserId, UUID friendUserId);
}
