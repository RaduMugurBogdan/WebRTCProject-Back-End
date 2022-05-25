package com.example.AccountAPI.repository.Mappers;


import com.example.AccountAPI.model.NotificationsToMembersModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NotificationsToMembersModelMapper implements RowMapper<NotificationsToMembersModel> {
    @Override
    public NotificationsToMembersModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationsToMembersModel notificationsToMembersModel=NotificationsToMembersModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setNotificationId(rs.getObject("notification_id",UUID.class))
                .setUserId(rs.getObject("user_id",UUID.class))
                .setSeen(rs.getBoolean("seen"));
        return notificationsToMembersModel;
    }
}
