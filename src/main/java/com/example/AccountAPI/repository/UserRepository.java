package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.interfaces.BasicRepository;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;

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

    private final String INSERT_USER_COMMAND = "INSERT INTO USERS (id,first_name, last_name, email,password) VALUES (:id,:firstName,:lastName,:email,:password);";
    private final String DELETE_USER_COMMAND = "DELETE FROM USERS WHERE id=:id";
    private final String UPDATE_USER_COMMAND = "UPDATE USERS SET first_name = :firstName, last_name = :lastName, email=:email WHERE id=:id";
    private final String FIND_USER_QUERY = "SELECT * FROM USERS id=:id";
    private final String CHECK_USER_BY_EMAIL="SELECT COUNT(*) FROM USERS WHERE email=:email;";
    private final String FIND_ALL_USERS = "SELECT * FROM USERS;";

    @Override
    public Optional<UUID> create(UserModel user) {
        UUID userId=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", userId)
                .addValue("firstName",user.getFirstName())
                .addValue("lastName",user.getLastName())
                .addValue("email",user.getEmail())
                .addValue("password",user.getPassword());

        template.update(INSERT_USER_COMMAND,namedParameters);
        return Optional.of(userId);
    }

    @Override
    public void delete(UserModel item) {

    }

    @Override
    public List<UserModel> getAll(){
        List <UserModel> users = template.query(FIND_ALL_USERS,userModelRowMapper);
        if(users.size()>0){
            return users;
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<UserModel> findOne(UUID id) {
        return null;
    }

    @Override
    public boolean exists(UUID id) {
        return false;
    }

    @Override
    public boolean findByEmail(String email) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email",email);
        if(template.queryForObject(CHECK_USER_BY_EMAIL,namedParameters,Integer.class)==1){
            return true;
        }
        return false;
    }
}
