package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.NotificationModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class NotificationsModelMapper implements RowMapper<NotificationModel> {
    @Override
    public NotificationModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        NotificationModel notificationModel=NotificationModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setContent(rs.getString("content"));
        return notificationModel;
    }
}
