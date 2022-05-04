package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.model.UserStatusModel;
import com.example.AccountAPI.repository.interfaces.UserStatusRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserStatusRepository implements UserStatusRepositoryInterface {
    private final String GET_USER_STATUS_QUERY ="SELECT * FROM USER_STATUS WHERE user_id=:user_id";
    private final String INSERT_USER_STATUS_COMMAND="INSERT INTO USER_STATUS (id, user_id, user_status, last_access_date) VALUES (:id, :user_id,:user_status, :last_access_date);";
    private final String UPDATE_USER_STATUS_COMMAND ="UPDATE USER_STATUS SET user_status=:user_status, last_access_date=:last_access_date WHERE user_id=:user_id;";
    private final String DELETE_USER_STATUS_COMMAND ="DELETE FROM USER_STATUS WHERE user_id=:user_id;";

    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private RowMapper<UserStatusModel> userStatusModelMapper;

    @Override
    public Optional<UserStatusModel> createUserStatus(UserStatusModel userStatus) {
        UUID id = UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("user_id", userStatus.getUserId())
                .addValue("user_status", userStatus.getUserStatus().toString())
                .addValue("last_access_date", userStatus.getLastAccessDate());
        if(template.update(INSERT_USER_STATUS_COMMAND, namedParameters)==1){
            userStatus.setId(id);
            return Optional.of(userStatus);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserStatusModel> getUserStatus(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_id", userId);
        try{
            UserStatusModel userStatusModel = template.queryForObject(GET_USER_STATUS_QUERY, namedParameters, userStatusModelMapper);
            return Optional.of(userStatusModel);
        }catch(IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void updateUserStatus(UserStatusModel userStatus) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userStatus.getId())
                .addValue("user_id", userStatus.getUserId())
                .addValue("user_status", userStatus.getUserStatus().toString())
                .addValue("last_access_date", userStatus.getLastAccessDate());
        template.update(UPDATE_USER_STATUS_COMMAND, namedParameters);
    }

    @Override
    public void deleteUserStatus(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("user_id", userId);
        template.update(DELETE_USER_STATUS_COMMAND, namedParameters);
    }
}
