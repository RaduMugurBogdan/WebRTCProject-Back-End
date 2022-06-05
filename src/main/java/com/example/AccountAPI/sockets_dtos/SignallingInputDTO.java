package com.example.AccountAPI.sockets_dtos;

import com.example.AccountAPI.util.SignallingServiceDeserializer;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class SignallingInputDTO {
    public String sendTo;
    public String type;
    @JsonDeserialize(using = SignallingServiceDeserializer.class)
    public String content;


    public boolean isValid(){
        if(sendTo!=null && type!=null && content!=null){
            return true;
        }
        return false;
    }
}
