package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.NotificationModel;
import com.example.AccountAPI.model.NotificationsToMembersModel;
import com.example.AccountAPI.repository.interfaces.GroupsNotificationsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class NotificationsRepository implements GroupsNotificationsRepositoryInterface {

    private static String CREATE_NOTIFICATION_COMMAND="INSERT INTO GROUPS_NOTIFICATIONS (id,content) VALUES (:id,:content);";
    private static String SET_NOTIFICATION_AS_SEEN_COMMAND="UPDATE GROUP_NOTIFICATIONS_TO_MEMBERS SET seen=TRUE WHERE notification_id=:notificationId AND user_id=:userId;";
    private static String DELETE_USER_NOTIFICATION_COMMAND="DELETE FROM GROUP_NOTIFICATIONS_TO_MEMBERS WHERE user_id=:userId AND notification_id=:notificationId;";
    private static String GET_ALL_USER_NOTIFICATIONS_QUERY="SELECT GROUPS_NOTIFICATIONS.id AS ID,content FROM GROUPS_NOTIFICATIONS JOIN GROUP_NOTIFICATIONS_TO_MEMBERS ON GROUPS_NOTIFICATIONS.id=notification_id WHERE user_id=:userId;";
    private static String ASSIGN_NOTIFICATION_TO_MEMBER_COMMAND="INSERT INTO GROUP_NOTIFICATIONS_TO_MEMBERS (id,notification_id,user_id,seen) VALUES (:id,:notificationId,:userId,FALSE);";

    @Autowired
    private RowMapper<NotificationModel> notificationModelRowMapper;
    private RowMapper<NotificationsToMembersModel> notificationsToMembersModelRowMapper;
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Optional<UUID> createNotification(String notificationContent) {
        UUID notificationId=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                                            .addValue("id",notificationId)
                                            .addValue("content",notificationContent);
        try{
            template.update(CREATE_NOTIFICATION_COMMAND, namedParameters);
        }catch (DataAccessException e){
            return Optional.empty();
        }
        return Optional.of(notificationId);
    }

    @Override
    public void setNotificationAsSeen(UUID notificationId, UUID memberId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("notificationId",notificationId)
                .addValue("userId",memberId);
        template.update(SET_NOTIFICATION_AS_SEEN_COMMAND, namedParameters);
    }

    @Override
    public void deleteNotification(UUID notificationId, UUID memberId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("notificationId",notificationId)
                .addValue("userId",memberId);
        template.update(DELETE_USER_NOTIFICATION_COMMAND, namedParameters);
    }

    @Override
    public List<NotificationModel> getAllUserNotifications(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId",userId);
        List<NotificationModel> notificationModels=template.query(GET_ALL_USER_NOTIFICATIONS_QUERY,namedParameters,notificationModelRowMapper);
        if(notificationModels==null){
            return Collections.emptyList();
        }
        return notificationModels;
    }

    @Override
    public Optional<UUID> assignNotificationToMember(UUID userId, UUID notificationId) {
        UUID id=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("userId",userId)
                .addValue("notificationId",notificationId);
        try{
            template.update(ASSIGN_NOTIFICATION_TO_MEMBER_COMMAND, namedParameters);
        }catch (DataAccessException e){
            return Optional.empty();
        }
        return Optional.of(id);
    }
}
