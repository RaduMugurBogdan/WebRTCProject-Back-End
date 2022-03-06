package com.example.AccountAPI.model;

import java.util.UUID;

public class PublicUserModel {
    private UUID id;
    private String firstName;
    private String lastName;

    public PublicUserModel(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PublicUserModel(){}

    public static PublicUserModel factory() {
        return new PublicUserModel();
    }

    public UUID getId() {
        return id;
    }

    public PublicUserModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public PublicUserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PublicUserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

}
