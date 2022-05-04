package com.example.AccountAPI.api;

import com.example.AccountAPI.repository.enums.UserStatusValue;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.service.interfaces.UserStatusServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class UserStatusController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserStatusServiceInterface userStatusService;
    @Autowired
    private UserServiceInterface userServiceInterface;

    @MessageMapping("/get_user_status")
    public void getUserStatus(SimpMessageHeaderAccessor sha,@Payload String userId){
        String currentUser=sha.getUser().getName();
        UUID id=UUID.fromString(userId);
        UserStatusValue userStatus=userStatusService.getUserStatus(id);
        simpMessagingTemplate.convertAndSendToUser(currentUser, "/queue/user_status_response/"+userId,userStatus.toString());
    }
    @MessageMapping("/set_user_status")
    public void setUserStatus(SimpMessageHeaderAccessor sha,@Payload String userStatus){
        String currentUser=sha.getUser().getName();
        UUID userId=userServiceInterface.getByUsername(currentUser).get().getId();
        userStatusService.setUserStatus(userId,UserStatusValue.valueOf(userStatus));
    }
    @MessageMapping("/set_user_last_access_time")
    public void setUserLastAccessTime(SimpMessageHeaderAccessor sha){
        String currentUser=sha.getUser().getName();
        UUID userId=userServiceInterface.getByUsername(currentUser).get().getId();
        userStatusService.updateUserLastAccessTime(userId);
        //userStatusService.setUserStatus(currentUser,UserStatusValue.valueOf(userStatus));
    }
}
