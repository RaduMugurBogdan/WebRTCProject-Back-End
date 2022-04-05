package com.example.AccountAPI.dto.input_dtos;

import java.util.UUID;

public class UserSearchDto {
    private UUID userId;
    private String content;

    public UserSearchDto(UUID userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
