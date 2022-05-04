package com.example.AccountAPI.model;

import java.util.Date;
import java.util.UUID;

public class MessageModel {
    private UUID id;
    private UUID chatId;
    private UUID senderId;
    private String messageContent;
    private Date sendingDate;

    public static MessageModel build(){
        return new MessageModel();
    }

    public UUID getId() {
        return id;
    }

    public MessageModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getChatId() {
        return chatId;
    }

    public MessageModel setChatId(UUID chatId) {
        this.chatId = chatId;
        return this;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public MessageModel setSenderId(UUID senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public MessageModel setMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public MessageModel setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
        return this;
    }
}
