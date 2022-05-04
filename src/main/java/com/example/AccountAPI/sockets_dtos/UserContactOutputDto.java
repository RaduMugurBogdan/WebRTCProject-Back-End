package com.example.AccountAPI.sockets_dtos;

import java.util.UUID;

public class UserContactOutputDto {
    private UUID chatId;
    private UUID userId;
    private String username;

    public static UserContactOutputDto build(){
        return new UserContactOutputDto();
    }

    public UUID getChatId() {
        return chatId;
    }

    public UserContactOutputDto setChatId(UUID chatId) {
        this.chatId = chatId;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserContactOutputDto setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserContactOutputDto setUsername(String username) {
        this.username = username;
        return this;
    }
}
