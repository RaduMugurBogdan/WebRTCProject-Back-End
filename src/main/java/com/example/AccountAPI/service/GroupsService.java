package com.example.AccountAPI.service;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.repository.interfaces.GroupsRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import com.example.AccountAPI.service.interfaces.GroupsServiceInterface;
import com.example.AccountAPI.sockets_dtos.GroupDetailsOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class GroupsService implements GroupsServiceInterface {
    @Autowired
    private GroupsRepositoryInterface groupsRepository;
    @Autowired
    private UsersRepositoryInterface usersRepository;

    @Override
    public UUID createGroup(UUID userId, List<UUID> guestsIds,String groupName) {
        UUID groupId=groupsRepository.create(userId,groupName);
        guestsIds.add(userId);
        for(UUID guestId :guestsIds){
            groupsRepository.addMemberToGroup(groupId,guestId);
        }
        return groupId;
    }



    @Override
    public Optional<GroupModel> getGroupById(UUID groupId) {
        return groupsRepository.getGroupById(groupId);
    }

    @Override
    public List<GroupModel> getUserGroups(UUID userId) {
        return groupsRepository.getUserGroups(userId);
    }

    @Override
    public boolean checkUserMembership(UUID groupId,UUID userId) {
        return this.groupsRepository.checkUserMembership(groupId,userId);
    }

    public List<PublicUserModel> getGroupMembers(UUID groupId){
        return groupsRepository.getAllGroupMembers(groupId);
    }
    public Optional<PublicUserModel> getGroupOwner(UUID groupId){
        Optional<GroupModel> groupModel=groupsRepository.getGroupById(groupId);
        if(!groupModel.isPresent()){
            return Optional.empty();
        }
        return usersRepository.getPublicUserModelById(groupModel.get().getUserId());
    }

    public void leaveGroup(UUID groupId,UUID userId){
        groupsRepository.removeUserFromGroup(groupId,userId);
    }
    public void deleteGroup(UUID groupId){
        groupsRepository.delete(groupId);
    }

    @Override
    public void updateGroup(UUID groupId, String groupName, List<UUID> groupMembersIds) {
        groupsRepository.updateGroupName(groupId,groupName);
        List<UUID> forDeletionMembers=groupsRepository.getAllGroupMembers(groupId).stream().filter(userModel->!groupMembersIds.contains(userModel.getId())).map(userModel->userModel.getId()).collect(Collectors.toList());
        UUID groupOwnerId=groupsRepository.getGroupOwner(groupId).get().getId();
        for(UUID memberId:forDeletionMembers){
            if(!groupOwnerId.equals(memberId)){
                groupsRepository.removeUserFromGroup(groupId,memberId);
            }
        }
        for(UUID memberId:groupMembersIds){
            if(!groupsRepository.checkUserMembership(groupId,memberId)){
                groupsRepository.addMemberToGroup(groupId,memberId);
            }
        }
    }
}
