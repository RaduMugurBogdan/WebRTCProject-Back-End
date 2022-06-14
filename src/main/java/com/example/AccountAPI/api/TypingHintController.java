package com.example.AccountAPI.api;

import com.example.AccountAPI.model.GroupsMessageModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.GroupsServiceInterface;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.sockets_dtos.GroupTypingHintOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
public class TypingHintController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private GroupsServiceInterface groupsService;
    @MessageMapping("/send_typing_hint_to_user")
    public void sendTypingHintToUser(SimpMessageHeaderAccessor sha, @Payload String username){
        Optional<UserModel> currentUserModel=userServiceInterface.getByUsername(sha.getUser().getName());
        if(!currentUserModel.isPresent()){
            return;
        }
        Optional<UserModel> userModel=userServiceInterface.getByUsername(username);
        if(!userModel.isPresent()){
            return;
        }
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/get_typing_hint_from_user/"+currentUserModel.get().getId(),currentUserModel.get().getId());
    }
    @MessageMapping("/send_typing_hint_to_group")
    public void sendTypingHintToGroup(SimpMessageHeaderAccessor sha, @Payload String groupId){
        if(!groupsService.getGroupById(UUID.fromString(groupId)).isPresent()){
            return;
        }
        groupsService.getGroupMembers(UUID.fromString(groupId)).forEach(publicUserModel -> {
            GroupTypingHintOutputDTO outputDTO=new GroupTypingHintOutputDTO();
            outputDTO.groupId=groupId;
            outputDTO.userId=sha.getUser().getName();
            if(!publicUserModel.getUsername().equals(sha.getUser().getName())){
                simpMessagingTemplate.convertAndSendToUser(publicUserModel.getUsername(), "/queue/get_group_typing_hint_from_user",outputDTO);
            }
        });
    }
}
