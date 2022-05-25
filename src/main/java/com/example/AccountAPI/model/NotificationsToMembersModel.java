package com.example.AccountAPI.model;

import java.util.UUID;

public class NotificationsToMembersModel {
    private UUID id;
    private UUID userId;
    private UUID notificationId;
    private boolean seen;

    public static NotificationsToMembersModel build(){
        return new NotificationsToMembersModel();
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    public NotificationsToMembersModel setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public NotificationsToMembersModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public NotificationsToMembersModel setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public boolean isSeen() {
        return seen;
    }

    public NotificationsToMembersModel setSeen(boolean seen) {
        this.seen = seen;
        return this;
    }
}
