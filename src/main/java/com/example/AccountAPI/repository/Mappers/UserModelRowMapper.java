package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.UserModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserModelRowMapper implements RowMapper<UserModel> {
    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserModel userModel = UserModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setUsername(rs.getString("username"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setPassword(rs.getString("password"))
                .setEmail(rs.getString("email"));
        return userModel;
    }
}
