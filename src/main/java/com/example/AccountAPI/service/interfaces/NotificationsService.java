package com.example.AccountAPI.service.interfaces;

import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.model.NotificationModel;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.interfaces.GroupsNotificationsRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.GroupsRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationsService implements NotificationsServiceInterface{
    @Autowired
    private GroupsRepositoryInterface groupsRepository;
    @Autowired
    private UsersRepositoryInterface usersRepository;
    @Autowired
    private GroupsNotificationsRepositoryInterface groupsNotificationsRepository;

    @Override
    public void addUsersToGroupNotification(UUID groupId, List<UUID> membersIds) {// trebuiesc create doua tipuri de notificari : una pentru persoanele adaugate in grup si una pentru a notifica membrii deja existenti ai grupului in privinta adaugarii noilor membrii in grup
        Optional<GroupModel> groupModel=groupsRepository.getGroupById(groupId);
        if(!groupModel.isPresent()){
            return;
        }
        Optional<UserModel> userModel=usersRepository.findOne(groupModel.get().getUserId());
        if(!userModel.isPresent()){
            return;
        }
        List<String> newMembersUsernames=new LinkedList<>();
        List<PublicUserModel> groupMembers=groupsRepository.getAllGroupMembers(groupId).stream().filter(publicUserModel->!membersIds.contains(publicUserModel.getId())).collect(Collectors.toList());
        String groupName=groupModel.get().getGroupName();
        String groupOwnerUsername=userModel.get().getUsername();
        for(UUID memberId : membersIds){
            userModel=usersRepository.findOne(memberId);
            if(userModel.isPresent()){
                newMembersUsernames.add(userModel.get().getUsername());
            }
        }
        String newUserNotificationContent=groupOwnerUsername+" added you to group "+groupName+".";
        String alreadyExistingUsersNotificationContent=groupOwnerUsername+" added ";
        int i=0;
        for(;i<newMembersUsernames.size()-1;i++){
            alreadyExistingUsersNotificationContent+=newMembersUsernames.get(i)+",";
        }
        alreadyExistingUsersNotificationContent+=newMembersUsernames.get(i);
        Optional<UUID> newUsersNotificationId=groupsNotificationsRepository.createNotification(newUserNotificationContent);
        Optional<UUID> alreadyExistingUsersNotificationId=groupsNotificationsRepository.createNotification(alreadyExistingUsersNotificationContent);
        if(newUsersNotificationId.isPresent()){
            for(UUID memberId : membersIds) {
                groupsNotificationsRepository.assignNotificationToMember(memberId,newUsersNotificationId.get());
            }
        }
        if(alreadyExistingUsersNotificationId.isPresent()){
            for(PublicUserModel model:groupMembers){
                groupsNotificationsRepository.assignNotificationToMember(model.getId(),alreadyExistingUsersNotificationId.get());
            }
        }
    }

    @Override
    public void deleteUsersFromGroupNotification(UUID groupId, List<UUID> membersIds) {
        List<String> deletedUsernames=new LinkedList<>();
        Optional<GroupModel> groupModel=groupsRepository.getGroupById(groupId);
        if(!groupModel.isPresent()){
            return;
        }
        Optional<PublicUserModel> groupOwner=groupsRepository.getGroupOwner(groupId);
        if(!groupOwner.isPresent()){
            return;
        }
        for(UUID memberId:membersIds){
            Optional<PublicUserModel> userModel=usersRepository.getPublicUserModelById(memberId);
            if(userModel.isPresent()){
                deletedUsernames.add(userModel.get().getUsername());
            }
        }

        List<PublicUserModel> groupMembers=groupsRepository.getAllGroupMembers(groupId);
        String notificationContent=groupOwner.get().getUsername() + " deleted " + (deletedUsernames.size()>2?"users ":"user ");
        int i=0;
        for(;i<deletedUsernames.size()-1;i++){
            notificationContent+=deletedUsernames.get(i)+",";
        }
        notificationContent+=deletedUsernames.get(i);
        notificationContent+=" from group "+groupModel.get().getGroupName()+".";
        Optional<UUID> removeUsersNotificationId=groupsNotificationsRepository.createNotification(notificationContent);
        if(!removeUsersNotificationId.isPresent()){
            return;
        }
        for(PublicUserModel publicUserModel:groupMembers){
            groupsNotificationsRepository.assignNotificationToMember(publicUserModel.getId(),removeUsersNotificationId.get());
        }
        Optional<PublicUserModel> groupOwnerModel=groupsRepository.getGroupOwner(groupModel.get().getUserId());
        if(!groupOwnerModel.isPresent()){
            return;
        }
        notificationContent=groupOwnerModel.get().getUsername()+" deleted you from group "+groupModel.get().getGroupName();
        removeUsersNotificationId=groupsNotificationsRepository.createNotification(notificationContent);
        if(!removeUsersNotificationId.isPresent()){
            return;
        }
        for(UUID memberId:membersIds){
            groupsNotificationsRepository.assignNotificationToMember(memberId,removeUsersNotificationId.get());
        }
    }

    @Override
    public void userLeavedGroupNotification(UUID groupId, UUID userId) {
        Optional<PublicUserModel> userModel=usersRepository.getPublicUserModelById(userId);
        Optional<GroupModel> groupModel=groupsRepository.getGroupById(groupId);
        if(!userModel.isPresent() || !groupModel.isPresent()){
            return;
        }
        List<PublicUserModel> groupMembersList=groupsRepository.getAllGroupMembers(groupId).stream().filter(model-> model.getId()!=userId).collect(Collectors.toList());
        String notificationContent="User "+userModel.get().getUsername()+" left group "+groupModel.get().getGroupName();
        Optional<UUID> notificationId=groupsNotificationsRepository.createNotification(notificationContent);
        if(!notificationId.isPresent()){
            return;
        }
        for(PublicUserModel model:groupMembersList){
            groupsNotificationsRepository.assignNotificationToMember(model.getId(),notificationId.get());
        }
    }

    @Override
    public void adminDeletedGroupNotification(UUID groupId) {
        Optional<PublicUserModel> groupOwner=groupsRepository.getGroupOwner(groupId);
        Optional<GroupModel> groupModel=groupsRepository.getGroupById(groupId);
        if(!groupOwner.isPresent() || ! groupModel.isPresent()){
            return;
        }
        List<PublicUserModel> groupMembers=groupsRepository.getAllGroupMembers(groupId).stream().filter(model-> groupOwner.get().getId()!=model.getId()).collect(Collectors.toList());
        String notificationContent=groupOwner.get().getUsername()+" deleted group "+groupModel.get().getGroupName();
        Optional<UUID> notificationId=groupsNotificationsRepository.createNotification(notificationContent);
        for(PublicUserModel model : groupMembers){
            groupsNotificationsRepository.assignNotificationToMember(model.getId(),notificationId.get());
        }
    }

    @Override
    public List<NotificationModel> getAllUserNotification(UUID userId) {
        return groupsNotificationsRepository.getAllUserNotifications(userId);
    }

   @Override
   public void deleteNotification(UUID notificationId,UUID memberId){
        groupsNotificationsRepository.deleteNotification(notificationId,memberId);
   }
}
