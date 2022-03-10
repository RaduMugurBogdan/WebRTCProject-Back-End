package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.repository.Mappers.GroupModelRowMapper;
import com.example.AccountAPI.repository.Mappers.PublicUserModelMapper;
import com.example.AccountAPI.repository.interfaces.GroupsRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/*
de verificat in serviciu daca user-ul care incearca sa sterga un grup este de asemenea cel care a creat grupul propriu-zis
* */


public class GroupsRepository implements GroupsRepositoryInterface {

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    GroupModelRowMapper groupModelRowMapper;
    @Autowired
    PublicUserModelMapper publicUserModelMapper;

    private final String GET_GROUP_MEMBERS_QUERY="SELECT USERS.id,USERS.first_name,USERS.last_name FROM USERS JOIN (SELECT id FROM GROUPS_MEMBERS WHERE id=:groupId) AS GM ON GM.user_id=USERS.id;";
    private final String GET_USER_OWNED_QUERY="SELECT * FROM GROUPS WHERE user_id=:userId;";
    private final String CREATE_GROUP_COMMAND="INSERT INTO GROUPS (id,group_name,user_id) VALUES (:id,:groupName,:userId);";
    private final String DELETE_GROUP_COMMAND="DELETE FROM GROUPS WHERE id=:id;";


    @Override
    public UUID create(GroupModel group) {
        UUID id=UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("groupName",group.getGroupName())
                .addValue("userId",group.getUserId());
        template.update(CREATE_GROUP_COMMAND,namedParameters);
        return id;
    }

    @Override
    public void delete(UUID id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id",id);
        template.update(DELETE_GROUP_COMMAND,namedParameters);
    }

    @Override
    public Optional<GroupModel> findOne(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<GroupModel> getAllOwnedGroups(UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId",userId);
        List <GroupModel>  groups = template.query(GET_GROUP_MEMBERS_QUERY,namedParameters,groupModelRowMapper);
        if(groups.size()==0){
            return Collections.emptyList();
        }
        return groups;
    }

    public List<PublicUserModel> getAllGroupMembers(UUID userId){
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId",userId);
        List <PublicUserModel>  members = template.query(GET_USER_OWNED_QUERY,namedParameters,publicUserModelMapper);
        if(members.size()==0){
            return Collections.emptyList();
        }
        return members;
    }

    @Override
    public boolean exists(UUID groupId, String groupName) {
        return false;
    }
}
