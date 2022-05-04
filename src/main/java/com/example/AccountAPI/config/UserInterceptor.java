package com.example.AccountAPI.config;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.util.JwtUtilsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Component
public class UserInterceptor implements ChannelInterceptor {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private JwtUtilsInterface jwtUtils;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
            if (raw instanceof Map) {
                Object name = ((Map) raw).get("user_token");
                if (name instanceof ArrayList) {
                    String username = jwtUtils.validateTokenAndRetrieveSubject(((ArrayList<String>) name).get(0));
                    Optional<UserModel> result = userServiceInterface.getByUsername(username);
                    if (result.isPresent()) {
                        accessor.setUser(result.get());
                    }
                }
            }
        }
        return message;
    }
}
