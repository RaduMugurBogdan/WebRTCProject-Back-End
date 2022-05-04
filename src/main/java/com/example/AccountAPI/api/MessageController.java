package com.example.AccountAPI.api;


import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.service.DMChatsService;
import com.example.AccountAPI.service.interfaces.DMChatsServiceInterface;
import com.example.AccountAPI.sockets_dtos.SendMessageInputDto;
import com.example.AccountAPI.dto.output_dtos.OutputSimpleTextMessage;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private DMChatsServiceInterface DMChatService;

    @MessageMapping("/binary_message")
    public void getBinaryMessage(){
        System.out.println("Binary message received---------------------------------------");
    }

    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        String message = "Hello from " + sha.getUser().getName();
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

    @MessageMapping("/get_users_like")
    @SendToUser("/queue/users_like_results")
    public List<PublicUserModel> sendUsersLike(@Payload String searchingWord) {
        return userService.getUsersLike(searchingWord);
    }

    @MessageMapping("/send_message_to_user")
    public void sendMessage(SimpMessageHeaderAccessor sha, @Payload SendMessageInputDto dto){
        UUID currentUserId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID otherUserId = userService.getByUsername(dto.destUsername).get().getId();
        Optional<DMChatsModel> chat=DMChatService.getChatByMembersId(currentUserId,otherUserId);
        Date date=new Date();
        if(!chat.isPresent()){
            DMChatsModel newDmChatsModel=DMChatsModel
                    .build()
                    .setId(UUID.randomUUID())
                    .setUser1Id(currentUserId)
                    .setUser2Id(otherUserId);
            Optional<UUID> chat_id=DMChatService.createDMChatRecord(newDmChatsModel);
            DMChatService.createMessage(chat_id.get(),currentUserId,dto.message,date);
            simpMessagingTemplate.convertAndSendToUser(dto.destUsername, "/queue/add_new_contact",userService.getByUsername(sha.getUser().getName()).get() );
            simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/add_new_contact", userService.getByUsername(dto.destUsername).get());
        }else{
            DMChatService.createMessage(chat.get().getId(),currentUserId,dto.message,date);
        }

        OutputSimpleTextMessage ostp=new OutputSimpleTextMessage();
        ostp.senderUsername=sha.getUser().getName();
        ostp.message=dto.message;
        ostp.date=date;
        simpMessagingTemplate.convertAndSendToUser(dto.destUsername, "/queue/received_messages/"+ostp.senderUsername, ostp);
    }

    @MessageMapping("/get_chat_messages")
    public void getChatMessages(SimpMessageHeaderAccessor sha, @Payload String username){
        UUID currentUserId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID otherUserId = userService.getByUsername(username).get().getId();
        List<MessageModel> messagesList=DMChatService.getChatMessages(currentUserId,otherUserId);
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/get_chat_messages/"+username,messagesList);
    }
}
