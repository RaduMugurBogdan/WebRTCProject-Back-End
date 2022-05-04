package com.example.AccountAPI.sockets_dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
@JsonPropertyOrder({ "groupName", "guestsIds" })
public class CreateGroupInputDto {
    public String groupName;
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    public List<String> guestsIds;
}
