package com.example.AccountAPI.service;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.repository.interfaces.DMChatsRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import com.example.AccountAPI.service.interfaces.DMChatsServiceInterface;
import com.example.AccountAPI.sockets_dtos.UserContactOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DMChatsService implements DMChatsServiceInterface {
    @Autowired
    private DMChatsRepositoryInterface DMChatRepository;
    @Autowired
    private UsersRepositoryInterface usersRepository;
    @Override
    public Optional<DMChatsModel> getChatByMembersId(UUID user1Id,UUID user2Id) {
        return this.DMChatRepository.getChatByMembersId(user1Id,user2Id);
    }

    @Override
    public Optional<UUID> createDMChatRecord(DMChatsModel dmChatsModel) {
        return this.DMChatRepository.createDMChatRecord(dmChatsModel);
    }

    @Override
    public List<UserContactOutputDto> getAllUserChats(UUID userId) {
        List<DMChatsModel> chats =  this.DMChatRepository.getAllUserChats(userId);
        List<UserContactOutputDto> collect;
        collect = chats.stream().map((DMChatsModel model) ->{
            String username=this.usersRepository.findOne(userId).get().getUsername();
            UUID contactId=model.getUser1Id();
            if(contactId.equals(userId)){
                contactId=model.getUser2Id();
            }
            return UserContactOutputDto.build().setChatId(model.getId()).setUserId(contactId).setUsername(usersRepository.findOne(contactId).get().getUsername());
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public Optional<UUID> createMessage(UUID chatId, UUID senderId, String message, Date date) {
        return DMChatRepository.createMessage(chatId,senderId,message,date);
    }

    @Override
    public List<MessageModel> getChatMessages(UUID user1Id, UUID user2Id) {
        Optional<DMChatsModel> dmChatsModel=getChatByMembersId(user1Id,user2Id);
        if(!dmChatsModel.isPresent()){
            return Collections.emptyList();
        }
        return DMChatRepository.getChatMessages(dmChatsModel.get().getId());
    }
}
