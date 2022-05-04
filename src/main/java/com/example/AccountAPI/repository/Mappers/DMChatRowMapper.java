package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.DMChatsModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class DMChatRowMapper implements RowMapper<DMChatsModel> {
    @Override
    public DMChatsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        DMChatsModel dmChatsModel=DMChatsModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setUser1Id(rs.getObject("user_1_id",UUID.class))
                .setUser2Id(rs.getObject("user_2_id",UUID.class));
        return dmChatsModel;
    }
}