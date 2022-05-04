package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
de adaugat  un camp pentru parola
de facut o interogare care verifica daca ub cobt exusta dupa email
+procedura de validare a datelor  user-ului inaibte de a fi inbtroduse in baza de date
 */


@SpringBootApplication
public class UserRepository implements UsersRepositoryInterface {
    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private RowMapper<UserModel> userModelRowMapper;
    @Autowired
    private RowMapper<PublicUserModel> publicUserModelMapper;

    private final String INSERT_USER_COMMAND = "INSERT INTO USERS (id, username, first_name, last_name, email,password) VALUES (:id,:username,:firstName,:lastName,:email,:password);";
    private final String DELETE_USER_COMMAND = "DELETE FROM USERS WHERE id=:id";
    private final String UPDATE_USER_COMMAND = "UPDATE USERS SET first_name = :firstName, last_name = :lastName, email=:email WHERE id=:id;";
    private final String FIND_USER_BY_ID_QUERY = "SELECT * FROM USERS WHERE id=:id;";
    private final String FIND_USER_BY_USERNAME_QUERY = "SELECT * FROM USERS WHERE username=:username;";
    private final String CHECK_USER_BY_EMAIL = "SELECT COUNT(*) FROM USERS WHERE email=:email;";
    private final String CHECK_USER_BY_USERNAME = "SELECT COUNT(*) FROM USERS WHERE username=:username;";
    private final String FIND_ALL_USERS = "SELECT * FROM USERS;";
    private final String GET_USERS_LIKE = "SELECT * FROM USERS WHERE LOWER(USERNAME) LIKE :word";

    @Override
    public Optional<UUID> create(UserModel user) {
        UUID userId = UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("username", user.getUsername())
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword());

        template.update(INSERT_USER_COMMAND, namedParameters);
        return Optional.of(userId);
    }

    @Override
    public void delete(UserModel item) {

    }

    @Override
    public List<UserModel> getAll() {
        List<UserModel> users = template.query(FIND_ALL_USERS, userModelRowMapper);
        if (users.size() > 0) {
            return users;
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<UserModel> findOne(UUID id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
        UserModel userModel = template.queryForObject(FIND_USER_BY_ID_QUERY , namedParameters, userModelRowMapper);
        if (userModel == null) {
            return Optional.empty();
        }
        return Optional.of(userModel);
    }

    @Override
    public boolean exists(UUID id) {
        return false;
    }

    @Override
    public boolean findByEmail(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
        if (template.queryForObject(CHECK_USER_BY_EMAIL, namedParameters, Integer.class) == 1) {
            return true;
        }
        return false;
    }


    @Override
    public boolean findByUsername(String username) {

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
        if (template.queryForObject(CHECK_USER_BY_USERNAME, namedParameters, Integer.class) == 1) {
            return true;
        }
        return false;
    }


    @Override
    public Optional<UserModel> getByUsername(String username) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
        try {
            UserModel userModel = template.queryForObject(FIND_USER_BY_USERNAME_QUERY, namedParameters, userModelRowMapper);
            return Optional.of(userModel);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PublicUserModel> getPublicUserModelById(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", userId);
        PublicUserModel userModel = template.queryForObject(FIND_USER_BY_ID_QUERY , namedParameters, publicUserModelMapper);
        if (userModel == null) {
            return Optional.empty();
        }
        return Optional.of(userModel);
    }

    @Override
    public List<PublicUserModel> getUsersLike(String like) {
        like = like.toLowerCase();
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("word", like + "%");

        List<PublicUserModel> users = template.query(GET_USERS_LIKE, namedParameters, publicUserModelMapper);
        if (!users.isEmpty()) {
            return users;
        }
        return Collections.emptyList();
    }
}





















