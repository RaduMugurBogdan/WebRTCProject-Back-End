package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.UserStatusModel;
import com.example.AccountAPI.repository.enums.UserStatusValue;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class UserStatusModelMapper implements RowMapper<UserStatusModel> {
    @Override
    public UserStatusModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserStatusModel userStatusModel = UserStatusModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setUserId(rs.getObject("user_id", UUID.class))
                .setUserStatus(UserStatusValue.valueOf(rs.getString("user_status")))
                .setLastAccessDate(rs.getDate("last_access_date"));
        return userStatusModel;
    }
}