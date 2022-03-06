package com.example.AccountAPI.dto.output_dtos;

import java.util.UUID;

public class GetUserOutputDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

    public GetUserOutputDto(UUID id,String firstName, String lastName, String email) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
