package com.example.AccountAPI.model;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.UUID;

public class UserModel implements Principal {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserModel() {

    }

    public UserModel(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static UserModel build() {
        return new UserModel();
    }

    public UserModel(UUID id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserModel(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public UserModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
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

    public UserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getName() {
        return this.getUsername();
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
