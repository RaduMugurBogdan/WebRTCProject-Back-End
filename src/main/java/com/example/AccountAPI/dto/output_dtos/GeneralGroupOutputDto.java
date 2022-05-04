package com.example.AccountAPI.dto.output_dtos;

import java.util.UUID;

public class GeneralGroupOutputDto {
    public UUID groupId;
    public UUID ownerId;
    public String groupName;

    public GeneralGroupOutputDto(UUID groupId,UUID ownerId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.ownerId=ownerId;
    }
}
