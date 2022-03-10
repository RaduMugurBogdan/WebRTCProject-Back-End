package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.repository.Mappers.PublicUserModelMapper;
import com.example.AccountAPI.repository.interfaces.FriendsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


/*

de verificat in friends service daca o relatie de prietenie deja exista inainte de a insera.

*/


public class FriendsRepository implements FriendsRepositoryInterface {
    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private PublicUserModelMapper publicUserModelMapper;

    private final String GET_ALL_FRIENDS_QUERY="SELECT USERS.id,USERS.first_name,USERS.last_name FROM USERS JOIN (SELECT user_id from FRIENDS WHERE user_id=:id) AS FRIENDS_IDS ON USERS.id=FRIENDS_IDS.user_id;";
    private final String CHECK_RECORD_BY_USERS_QUERY ="SELECT COUNT(*) FROM FRIENDS user_id=:currentUserId AND friend_user_id=:friendUserId;";
    private final String INSERT_FRIENDS_COMMAND="INSERT INTO FRIENDS (id,user_id,friend_user_id) VALUES (:id,:currentUserId,:friendUserId);";
    private final String DELETE_FRIENDS_COMMAND="DELETE FROM USERS WHERE user_id=:currentUserId AND friend_user_id=:friend_user_id;";

    @Override
    public List<PublicUserModel> getAllFriends(UUID userId) {
        List <PublicUserModel> usersIds = template.query(GET_ALL_FRIENDS_QUERY,publicUserModelMapper);
        if(usersIds.size()==0){
            return Collections.emptyList();
        }
        return usersIds;
    }

    @Override
    public UUID create(UUID currentUserId, UUID friendUserId) {
        UUID id=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("currentUserId",currentUserId)
                .addValue("friendUserId",friendUserId);
        template.update(INSERT_FRIENDS_COMMAND,namedParameters);
        return id;
    }

    @Override
    public boolean exists(UUID currentUserId, UUID friendUserId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("currentUserId",currentUserId)
                .addValue("friendUserid",friendUserId);
        if(template.queryForObject(CHECK_RECORD_BY_USERS_QUERY,namedParameters,Integer.class)==1){
            return true;
        }
        return false;
    }

    @Override
    public void delete(UUID currentUserId, UUID friendUserId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("currentUserId",currentUserId)
                .addValue("friendUserId",friendUserId);
        template.update(DELETE_FRIENDS_COMMAND,namedParameters);
    }

}
