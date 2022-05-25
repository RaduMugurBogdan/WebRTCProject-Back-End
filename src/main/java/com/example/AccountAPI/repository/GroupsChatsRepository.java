package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.GroupsMessageModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.repository.interfaces.GroupsChatsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

@SpringBootApplication
public class GroupsChatsRepository implements GroupsChatsRepositoryInterface {
    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private RowMapper<GroupsMessageModel> groupsMessageModelRowMapper;

    private final String CREATE_MESSAGE_COMMAND="INSERT INTO GROUPS_MESSAGES(id,group_id,sender_id,message_content,sending_date,attachments) VALUES (:id,:group_id,:sender_id,:message_content,:sending_date,:attachments);";
    private final String GET_ALL_GROUP_MESSAGES_QUERY="SELECT * FROM GROUPS_MESSAGES WHERE group_id=:group_id;";

    @Override
    public Optional<UUID> createGroupChatMessage(UUID groupId, UUID senderId, String message, Date date) {
        UUID recordId=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", recordId)
                .addValue("group_id",groupId)
                .addValue("sender_id",senderId)
                .addValue("message_content",message)
                .addValue("sending_date",date)
                .addValue("attachments",false);
        try{
            template.update(CREATE_MESSAGE_COMMAND,namedParameters);
            return Optional.of(recordId);
        }catch (DataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<GroupsMessageModel> getGroupsChatMessages(UUID groupId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("group_id", groupId);
        List<GroupsMessageModel> queryResult=template.query(GET_ALL_GROUP_MESSAGES_QUERY,namedParameters,groupsMessageModelRowMapper);
        if(queryResult.isEmpty()){
            return Collections.emptyList();
        }
        return queryResult;
    }
}
