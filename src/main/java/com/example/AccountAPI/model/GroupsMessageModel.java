package com.example.AccountAPI.model;

import java.util.Date;
import java.util.UUID;

public class GroupsMessageModel {
    private UUID id;
    private UUID groupId;
    private UUID senderId;
    private String messageContent;
    private Date sendingDate;
    private boolean attachments;

    public static GroupsMessageModel build(){
        return new GroupsMessageModel();
    }

    public UUID getId() {
        return id;
    }

    public GroupsMessageModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public GroupsMessageModel setGroupId(UUID groupId) {
        this.groupId = groupId;
        return this;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public GroupsMessageModel setSenderId(UUID senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public GroupsMessageModel setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public GroupsMessageModel setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
        return this;
    }

    public boolean isAttachments() {
        return attachments;
    }

    public GroupsMessageModel setAttachments(boolean attachments) {
        this.attachments = attachments;
        return this;
    }
}
