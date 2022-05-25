package com.example.AccountAPI.model;

import java.util.UUID;

public class NotificationModel {
    private UUID id;
    private String content;

    public static NotificationModel build(){
        return new NotificationModel();
    }

    public UUID getId() {
        return id;
    }

    public NotificationModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public NotificationModel setContent(String content) {
        this.content = content;
        return this;
    }
}
