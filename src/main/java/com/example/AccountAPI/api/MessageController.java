package com.example.AccountAPI.api;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserServiceInterface userService;
    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        String message = "Hello from " + sha.getUser().getName();
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

    @MessageMapping("/get_all_users")
    @SendToUser("/queue/all_users")
    public List<UserModel> getUsers(){
        return userService.getAllUsers();
    }
}
