package com.example.AccountAPI.dto.output_dtos;

import java.util.UUID;

public class CreateUserOutputDto {
    private UUID userId;

    public CreateUserOutputDto(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
