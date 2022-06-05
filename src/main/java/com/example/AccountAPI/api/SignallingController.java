package com.example.AccountAPI.api;

import com.example.AccountAPI.sockets_dtos.SignallingInputDTO;
import com.example.AccountAPI.sockets_dtos.SignallingOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SignallingController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/send_signalling_message")
    public void sendSignallingMessageToUser(SimpMessageHeaderAccessor sha, @Payload SignallingInputDTO input){
        if(input!=null && input.isValid()) {
            SignallingOutputDTO outputDTO=new SignallingOutputDTO();
            outputDTO.sender=input.sendTo;
            outputDTO.type=input.type;
            outputDTO.content=input.content;
            simpMessagingTemplate.convertAndSendToUser(input.sendTo, "/queue/get_signalling_message",outputDTO);
        }
    }
}
