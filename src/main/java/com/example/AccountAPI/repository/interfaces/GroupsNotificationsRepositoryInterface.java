package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.NotificationModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupsNotificationsRepositoryInterface {
    Optional<UUID> createNotification(String notificationContent);
    void setNotificationAsSeen(UUID notificationId,UUID memberId);
    void deleteNotification(UUID notificationId,UUID memberId);
    List<NotificationModel> getAllUserNotifications(UUID userId);
    Optional<UUID> assignNotificationToMember(UUID userId,UUID notificationId);
}
