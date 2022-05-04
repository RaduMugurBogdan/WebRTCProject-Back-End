package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.DMChatsModel;
import com.example.AccountAPI.model.MessageModel;
import com.example.AccountAPI.repository.interfaces.DMChatsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

@SpringBootApplication
public class DMChatsRepository  implements DMChatsRepositoryInterface {
    private final String GET_ALL_DM_CHATS_BY_USERNAME_QUERY="SELECT * FROM DM_CHATS WHERE user_1_id=:userId OR user_2_id=:userId;";
    private final String GET_DM_CHAT_BY_MEMBERS_IDS_QUERY="SELECT * FROM DM_CHATS WHERE (user_1_id=:user_1_Id AND user_2_id=:user_2_Id) OR (user_1_id=:user_2_Id AND user_2_id=:user_1_Id);";
    private final String INSERT_DM_CHAT_BY_USERNAME_COMMAND = "INSERT INTO DM_CHATS (id, user_1_id, user_2_id) VALUES (:id,:user_1_id,:user_2_id);";
    private final String INSERT_MESSAGE_COMMAND = "INSERT INTO MESSAGES (id, chat_id,sender_id,message_content,sending_date) VALUES (:id, :chat_id,:sender_id,:message_content,:sending_date);";
    private final String GET_CHAT_MESSAGES_QUERY="SELECT * FROM MESSAGES WHERE chat_id=:chat_id ORDER BY sending_date ASC;";
    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private RowMapper<DMChatsModel> DMChatModelMapper;
    @Autowired
    private RowMapper<MessageModel> MessageModelMapper;
    @Override
    public Optional<DMChatsModel> getChatByMembersId(UUID user1Id,UUID user2Id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_1_Id",user1Id)
                .addValue("user_2_Id",user2Id);
        try {
            DMChatsModel dmChatsModel = template.queryForObject(GET_DM_CHAT_BY_MEMBERS_IDS_QUERY, namedParameters,  DMChatModelMapper);
            if(dmChatsModel==null){
                return Optional.empty();
            }
            return Optional.of(dmChatsModel);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DMChatsModel> getAllUserChats(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId",userId);
        List<DMChatsModel>  dmChats = template.query(GET_ALL_DM_CHATS_BY_USERNAME_QUERY, namedParameters,DMChatModelMapper);
        if (dmChats.size() > 0) {
            return dmChats;
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<UUID> createDMChatRecord(DMChatsModel dmChatsModel) {
        UUID userId = UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("user_1_id", dmChatsModel.getUser1Id())
                .addValue("user_2_id", dmChatsModel.getUser2Id());
        if (template.update(INSERT_DM_CHAT_BY_USERNAME_COMMAND, namedParameters) == 1) {
            return Optional.of(userId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UUID> createMessage(UUID chatId,UUID senderId ,String message, Date date) {
        UUID id = UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("chat_id", chatId)
                .addValue("sender_id",senderId)
                .addValue("message_content",message)
                .addValue("sending_date",date);
        if(template.update(INSERT_MESSAGE_COMMAND, namedParameters) == 1) {
            return Optional.of(id);
        }
        return Optional.empty();
    }

    @Override
    public List<MessageModel> getChatMessages(UUID chatId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("chat_id",chatId);
        List<MessageModel>  dmChats = template.query(GET_CHAT_MESSAGES_QUERY,namedParameters,MessageModelMapper);
        if (dmChats.size() > 0) {
            return dmChats;
        }
        return Collections.emptyList();
    }

}
