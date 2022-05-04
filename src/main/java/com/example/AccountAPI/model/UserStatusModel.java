package com.example.AccountAPI.model;

import com.example.AccountAPI.repository.enums.UserStatusValue;

import java.util.Date;
import java.util.UUID;

public class UserStatusModel {
    private UUID id;
    private UUID userId;
    private UserStatusValue userStatus;
    private Date lastAccessDate;

    public UserStatusValue getUserStatus() {
        return userStatus;
    }

    public UserStatusModel setUserStatus(UserStatusValue userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public static UserStatusModel build(){
        return new UserStatusModel();
    }

    public UUID getId() {
        return id;
    }

    public UserStatusModel setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public UserStatusModel setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }


    public Date getLastAccessDate() {
        return lastAccessDate;
    }

    public UserStatusModel setLastAccessDate(Date lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
        return this;
    }


}
