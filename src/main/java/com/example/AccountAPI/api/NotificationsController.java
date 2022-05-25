package com.example.AccountAPI.api;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.NotificationModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.NotificationsServiceInterface;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.management.Notification;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class NotificationsController {
    /*
    this method will return all the notifications
        ->group notifications
        ->new contacts notifications
     */
    @Autowired
    private NotificationsServiceInterface notificationsService;
    @Autowired
    private UserServiceInterface userService;
    @MessageMapping("/get_notifications")
    @SendToUser("/queue/get_notifications")
    public List<NotificationModel> getAllNotifications(SimpMessageHeaderAccessor sha){
        Optional<UserModel> userModel=userService.getByUsername(sha.getUser().getName());
        if(userModel.isPresent()){
            return notificationsService.getAllUserNotification(userModel.get().getId());
        }
        return Collections.emptyList();
    }
    @MessageMapping("/delete_notification")
    public void deleteNotification(SimpMessageHeaderAccessor sha,@Payload String notificationId){
        Optional<UserModel> userModel=userService.getByUsername(sha.getUser().getName());
        if(!userModel.isPresent()){
            return;
        }
        UUID userId=userModel.get().getId();
        UUID deletedNotificationId=UUID.fromString(notificationId);
        notificationsService.deleteNotification(deletedNotificationId,userId);
    }

}
