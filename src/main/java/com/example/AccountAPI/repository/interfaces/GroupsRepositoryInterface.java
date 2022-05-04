package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupsRepositoryInterface {

    Optional<GroupModel> getGroupById(UUID GroupId);

    UUID create(UUID userId,String groupName);

    void addMemberToGroup(UUID groupId,UUID guestId);

    void delete(UUID id);

    Optional<PublicUserModel> getGroupOwner(UUID groupId);

    Optional<GroupModel> findOne(UUID id);

    List<GroupModel> getAllOwnedGroups(UUID id);

    boolean exists(UUID groupId, String groupName);

    boolean checkUserMembership(UUID groupId,UUID userId);

    List<PublicUserModel> getAllGroupMembers(UUID groupId);
    public List<GroupModel> getUserGroups(UUID userId);

    void removeUserFromGroup(UUID groupId,UUID userId);

    void updateGroupName(UUID groupId,String groupName);
}
