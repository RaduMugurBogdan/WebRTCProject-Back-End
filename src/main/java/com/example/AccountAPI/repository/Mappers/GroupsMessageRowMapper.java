package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.GroupsMessageModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
@Component
public class GroupsMessageRowMapper implements RowMapper<GroupsMessageModel> {
    @Override
    public GroupsMessageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        GroupsMessageModel groupsMessageModel=GroupsMessageModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setMessageContent(rs.getString("message_content"))
                .setGroupId(rs.getObject("group_id",UUID.class))
                .setSenderId(rs.getObject("sender_id",UUID.class))
                .setSendingDate(rs.getDate("sending_date"))
                .setAttachments(rs.getBoolean("attachments"));
        return groupsMessageModel;
    }
}
