package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupsServiceInterface {
    UUID createGroup(UUID userId, List<UUID> guestsIds,String groupName);
    Optional<GroupModel> getGroupById(UUID groupId);
    List<GroupModel> getUserGroups(UUID userId);
    boolean checkUserMembership(UUID groupId,UUID userId);
    List<PublicUserModel> getGroupMembers(UUID groupId);
     Optional<PublicUserModel> getGroupOwner(UUID groupId);
    public void leaveGroup(UUID groupId,UUID userId);

    void deleteGroup(UUID groupId);

    void updateGroup(UUID groupId, String groupName, List<UUID> groupMembersIds);
}
