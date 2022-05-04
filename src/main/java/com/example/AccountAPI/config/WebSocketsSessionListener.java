package com.example.AccountAPI.config;

import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.service.interfaces.UserStatusServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.UUID;

@Component
public class WebSocketsSessionListener {
    @Autowired
    private UserStatusServiceInterface userStatusService;
    @Autowired
    private UserServiceInterface userService;
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

    }
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        UUID userId=userService.getByUsername(event.getUser().getName()).get().getId();
        userStatusService.deleteUserStatus(userId);
    }
}
