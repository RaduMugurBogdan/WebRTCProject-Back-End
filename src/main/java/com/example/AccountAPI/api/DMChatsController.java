package com.example.AccountAPI.api;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.DMChatsService;
import com.example.AccountAPI.service.interfaces.DMChatsServiceInterface;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.sockets_dtos.UserContactOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DMChatsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private DMChatsServiceInterface DMChatsService;
    @Autowired
    private UserServiceInterface userService;

    @MessageMapping("/get_user_contacts")
    @SendToUser("/queue/get_all_contacts")
    public List<UserContactOutputDto> getAllUserChats(SimpMessageHeaderAccessor sha){
        String username=sha.getUser().getName();
        UserModel userModel = userService.getByUsername(username).get();
        return DMChatsService.getAllUserChats(userModel.getId());
    }

}
