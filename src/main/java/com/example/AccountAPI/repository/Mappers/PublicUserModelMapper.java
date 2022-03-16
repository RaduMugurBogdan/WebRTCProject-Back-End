package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.PublicUserModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class PublicUserModelMapper implements RowMapper<PublicUserModel> {
    @Override
    public PublicUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PublicUserModel publicuserModel= PublicUserModel.factory()
                .setId(rs.getObject("id", UUID.class))
                .setUsername(rs.getString("username"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"));
        return publicuserModel;
    }
}
