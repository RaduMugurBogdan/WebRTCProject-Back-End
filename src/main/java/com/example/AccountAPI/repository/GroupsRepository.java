package com.example.AccountAPI.repository;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.repository.Mappers.GroupModelRowMapper;
import com.example.AccountAPI.repository.Mappers.PublicUserModelMapper;
import com.example.AccountAPI.repository.interfaces.GroupsRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;
import java.util.stream.Collectors;


@SpringBootApplication
public class GroupsRepository implements GroupsRepositoryInterface {

    @Autowired
    private NamedParameterJdbcTemplate template;
    @Autowired
    private GroupModelRowMapper groupModelRowMapper;
    @Autowired
    private PublicUserModelMapper publicUserModelMapper;
    @Autowired
    private UsersRepositoryInterface usersRepository;

    private final String GET_GROUP_OWNER_QUERRY="SELECT user_id from GROUPS WHERE id=:groupId;";
    private final String GET_GROUP_MEMBERS_QUERY = "SELECT USERS.id,USERS.username,USERS.first_name,USERS.last_name FROM USERS JOIN (SELECT user_id FROM GROUPS_MEMBERS WHERE group_id=:groupId) AS GM ON GM.user_id=USERS.id;";
    private final String GET_USER_OWNED_QUERY = "SELECT * FROM GROUPS WHERE user_id=:userId;";
    private final String CREATE_GROUP_COMMAND = "INSERT INTO GROUPS (id,group_name,user_id) VALUES (:id,:groupName,:userId);";
    private final String DELETE_GROUP_COMMAND = "DELETE FROM GROUPS WHERE id=:id;";
    private final String ADD_USER_TO_GROUP_COMMAND="INSERT INTO GROUPS_MEMBERS (id,group_id,user_id) VALUES (:id,:group_id,:guest_id);";
    private final String GET_GROUP_BY_ID_QUERRY="SELECT * FROM GROUPS WHERE id=:groupId;";
    private final String GET_USER_GROUPS_QUERY = "SELECT group_id FROM GROUPS_MEMBERS WHERE user_id=:userId;";
    private final String REMOVE_USER_FROM_GROUP_COMMAND ="DELETE FROM GROUPS_MEMBERS WHERE group_id=:group_id AND user_id=:user_id;";
    private final String UPDATE_GROUP_NAME_COMMAND="UPDATE GROUPS SET group_name=:group_name WHERE id=:group_id;";
    public List<GroupModel> getUserGroups(UUID userId){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("userId", userId);
        List<UUID> groupsIdsList=template.queryForList(GET_USER_GROUPS_QUERY,namedParameters,UUID.class);
        List<GroupModel> resultsList=new LinkedList<>();
        for(UUID groupId:groupsIdsList){
            Optional<GroupModel> result=getGroupById(groupId);
            if(result.isPresent()){
                resultsList.add(result.get());
            }
        }
        return resultsList;
    }

    @Override
    public void removeUserFromGroup(UUID groupId, UUID userId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("group_id", groupId)
                .addValue("user_id", userId);
        template.update(REMOVE_USER_FROM_GROUP_COMMAND,namedParameters);
    }

    @Override
    public void updateGroupName(UUID groupId,String groupName) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("group_id", groupId)
                .addValue("group_name", groupName);
        template.update(UPDATE_GROUP_NAME_COMMAND,namedParameters);
    }

    public Optional<GroupModel> getGroupById(UUID groupId){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("groupId", groupId);
        GroupModel groupModel;
        try {
            groupModel = template.queryForObject(GET_GROUP_BY_ID_QUERRY, namedParameters, groupModelRowMapper);
        }catch(IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
        if(groupModel==null) {
            return Optional.empty();
        }else{
            return Optional.of(groupModel);
        }
    }

    @Override
    public UUID create(UUID userId, String groupName) {
        UUID id = UUID.randomUUID();
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("groupName", groupName)
                .addValue("userId", userId);
        template.update(CREATE_GROUP_COMMAND, namedParameters);
        return id;
    }

    @Override
    public void addMemberToGroup(UUID groupId, UUID guestId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", UUID.randomUUID())
                .addValue("group_id",groupId)
                .addValue("guest_id",guestId)
                ;
        template.update(ADD_USER_TO_GROUP_COMMAND, namedParameters);
    }

    @Override
    public void delete(UUID id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        template.update(DELETE_GROUP_COMMAND, namedParameters);
    }

    @Override
    public Optional<PublicUserModel> getGroupOwner(UUID groupId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("groupId", groupId);
        UUID userId=template.queryForObject(GET_GROUP_OWNER_QUERRY,namedParameters,UUID.class);
        return usersRepository.getPublicUserModelById(userId);
    }

    @Override
    public Optional<GroupModel> findOne(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<GroupModel> getAllOwnedGroups(UUID userId) {/*
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("userId", userId);
        List<GroupModel> groups = template.query(GET_GROUP_MEMBERS_QUERY, namedParameters, groupModelRowMapper);
        if (groups.size() == 0) {
            return Collections.emptyList();
        }
        return groups;*/
        return null;
    }

    public List<PublicUserModel> getAllGroupMembers(UUID groupId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("groupId", groupId);
        List<PublicUserModel> membersList = template.query(GET_GROUP_MEMBERS_QUERY, namedParameters,publicUserModelMapper);
        if (membersList.size() == 0) {
            return Collections.emptyList();
        }
        return membersList;
    }

    @Override
    public boolean exists(UUID groupId, String groupName) {
        return false;
    }

    @Override
    public boolean checkUserMembership(UUID groupId, UUID userId) {
        return getAllGroupMembers(groupId).stream().map(publicUserModel -> publicUserModel.getId()).collect(Collectors.toList()).contains(userId);
    }
}
