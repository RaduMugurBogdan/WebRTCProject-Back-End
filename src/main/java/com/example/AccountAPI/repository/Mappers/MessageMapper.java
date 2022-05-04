package com.example.AccountAPI.repository.Mappers;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class MessageMapper  implements RowMapper<MessageModel> {
    @Override
    public MessageModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        MessageModel messageModel=MessageModel.build()
                .setId(rs.getObject("id", UUID.class))
                .setChatId(rs.getObject("chat_id",UUID.class))
                .setSenderId(rs.getObject("sender_id",UUID.class))
                .setMessageContent(rs.getString("message_content"))
                .setSendingDate(rs.getDate("sending_date"));
        return messageModel;
    }
}
