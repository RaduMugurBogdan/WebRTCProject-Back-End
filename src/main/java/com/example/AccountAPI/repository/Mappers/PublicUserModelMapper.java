package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.PublicUserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PublicUserModelMapper implements RowMapper<PublicUserModel> {
    @Override
    public PublicUserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        PublicUserModel publicuserModel= PublicUserModel.factory()
                .setId(rs.getObject("id", UUID.class))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"));
        return publicuserModel;
    }
}
