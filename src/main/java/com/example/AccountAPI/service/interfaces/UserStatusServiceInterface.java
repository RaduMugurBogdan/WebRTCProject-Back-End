package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.repository.enums.UserStatusValue;

import java.util.UUID;

public interface UserStatusServiceInterface {
    UserStatusValue getUserStatus(UUID userId);
    void setUserStatus(UUID userId,UserStatusValue userStatusValue);
    void updateUserLastAccessTime(UUID userId);
    void deleteUserStatus(UUID userId);
}
