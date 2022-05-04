package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.sockets_dtos.UserContactOutputDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DMChatsServiceInterface {
    Optional<DMChatsModel> getChatByMembersId(UUID user1Id,UUID user2Id);
    Optional<UUID> createDMChatRecord(DMChatsModel dmChatsModel);
    List<UserContactOutputDto> getAllUserChats(UUID userId);
    Optional<UUID> createMessage(UUID chatId,UUID senderId ,String message, Date date);
    List<MessageModel> getChatMessages(UUID user1Id,UUID user2Id);
}
