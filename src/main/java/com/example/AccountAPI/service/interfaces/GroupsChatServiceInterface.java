package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.GroupsMessageModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupsChatServiceInterface {
    Optional<UUID> createGroupChatMessage(UUID groupId, UUID senderId , String message, Date date);
    List<GroupsMessageModel> getGroupsChatMessages(UUID groupId);
}
