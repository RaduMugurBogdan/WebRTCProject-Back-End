package com.example.AccountAPI.model;

import java.util.UUID;

public class GroupModel {
    private UUID id;
    private UUID userId;
    private String groupName;

    public GroupModel(UUID id, UUID userId, String groupName) {
        this.id = id;
        this.userId = userId;
        this.groupName = groupName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
