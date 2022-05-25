package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.NotificationModel;

import java.util.List;
import java.util.UUID;

public interface NotificationsServiceInterface {
    void addUsersToGroupNotification(UUID groupId, List<UUID> membersIds);
    void deleteUsersFromGroupNotification(UUID groupId,List<UUID> membersIds);
    void userLeavedGroupNotification(UUID groupId,UUID userId);
    void adminDeletedGroupNotification(UUID groupId);
    List<NotificationModel> getAllUserNotification(UUID userId);
    void deleteNotification(UUID notificationId,UUID memberId);
}
