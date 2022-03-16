package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.GroupModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class GroupModelRowMapper implements RowMapper<GroupModel> {
    @Override
    public GroupModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GroupModel(rs.getObject("id",UUID.class),rs.getObject("user_id",UUID.class),rs.getString("group_name"));
    }
}
