package com.example.AccountAPI.api;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.sockets_dtos.UserContactOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Controller
public class RoomsManager {
    /*
    @Autowired
    private UserServiceInterface userService;
    private HashMap<UUID, HashSet<UUID>> rooms=new HashMap<>();


    @MessageMapping("/get_user_contacts")
    @SendToUser("/queue/get_all_contacts")
    public void connectToRoom(SimpMessageHeaderAccessor sha, @Payload String roomID){
        UUID roomId=UUID.fromString(roomID);
        String username=sha.getUser().getName();
        UUID userID = userService.getByUsername(username).get().getId();
        if(rooms.containsKey(roomId)){
            rooms.get(roomId).add(userID);
        }else{

        }
    }*/

}
