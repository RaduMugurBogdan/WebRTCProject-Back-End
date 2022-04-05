package com.example.AccountAPI.sockets_dtos;

import java.util.UUID;

public class ChatMessageDto {
    UUID chatId;
    UUID receiverId;
    String content;
    MessageType messageType;
}
