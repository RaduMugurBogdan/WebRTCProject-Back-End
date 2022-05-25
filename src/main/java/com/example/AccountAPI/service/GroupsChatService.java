package com.example.AccountAPI.service;

import com.example.AccountAPI.model.GroupsMessageModel;
import com.example.AccountAPI.repository.interfaces.GroupsChatsRepositoryInterface;
import com.example.AccountAPI.service.interfaces.GroupsChatServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GroupsChatService implements GroupsChatServiceInterface {
    @Autowired
    private GroupsChatsRepositoryInterface groupChatRepository;
    @Override
    public Optional<UUID> createGroupChatMessage(UUID groupId, UUID senderId, String message, Date date) {
        return groupChatRepository.createGroupChatMessage(groupId,senderId,message,date);
    }

    @Override
    public List<GroupsMessageModel> getGroupsChatMessages(UUID groupId) {
        return groupChatRepository.getGroupsChatMessages(groupId);
    }
}
