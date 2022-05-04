package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.sockets_dtos.ChatMessageDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DMChatsRepositoryInterface {
    Optional<DMChatsModel> getChatByMembersId(UUID user1Id,UUID user2Id);
    List<DMChatsModel> getAllUserChats(UUID userId);
    Optional<UUID> createDMChatRecord(DMChatsModel dmChatsModel);
    Optional<UUID> createMessage(UUID chatId,UUID senderId ,String message, Date date);
    List<MessageModel> getChatMessages(UUID chatId);
}
