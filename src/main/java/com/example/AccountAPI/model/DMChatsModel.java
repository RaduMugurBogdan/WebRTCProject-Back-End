package com.example.AccountAPI.model;

import java.util.UUID;

public class DMChatsModel {
    private UUID id;
    private UUID user1Id;
    private UUID user2Id;

    public static DMChatsModel build(){
        return new DMChatsModel();
    }

    public UUID getId() {
        return id;
    }

    public DMChatsModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUser1Id() {
        return user1Id;
    }

    public DMChatsModel setUser1Id(UUID user1Id) {
        this.user1Id = user1Id;
        return this;
    }

    public UUID getUser2Id() {
        return user2Id;
    }

    public DMChatsModel setUser2Id(UUID user2Id) {
        this.user2Id = user2Id;
        return this;
    }
}
