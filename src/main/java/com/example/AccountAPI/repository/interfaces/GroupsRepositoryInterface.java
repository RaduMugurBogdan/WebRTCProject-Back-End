package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupsRepositoryInterface {
    UUID create(GroupModel group);
    void delete(UUID id);
    Optional<GroupModel> findOne(UUID id);
    List<GroupModel> getAllOwnedGroups(UUID id);
    boolean exists(UUID groupId,String groupName);
    List<PublicUserModel> getAllGroupMembers(UUID userId);
}
