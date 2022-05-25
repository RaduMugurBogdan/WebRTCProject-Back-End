package com.example.AccountAPI.api;


import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.service.interfaces.NotificationsServiceInterface;
import com.example.AccountAPI.sockets_dtos.CreateGroupInputDto;
import com.example.AccountAPI.model.GroupModel;
import com.example.AccountAPI.service.interfaces.GroupsServiceInterface;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.sockets_dtos.GroupDetailsOutputDto;
import com.example.AccountAPI.sockets_dtos.UpdateGroupInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class GroupsController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private GroupsServiceInterface groupsService;
    @Autowired
    private NotificationsServiceInterface notificationService;

    @MessageMapping("/create_group")
    @SendToUser("/queue/new_group_created")
    public GroupModel createGroup(SimpMessageHeaderAccessor sha, @Payload CreateGroupInputDto dto) {
        UUID userId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID groupId=groupsService.createGroup(userId,dto.guestsIds.stream().map((String textUUID)->UUID.fromString(textUUID)).collect(Collectors.toList()), dto.groupName);
        notificationService.addUsersToGroupNotification(groupId,dto.guestsIds.stream().map((String textUUID)->UUID.fromString(textUUID)).collect(Collectors.toList()));
        return groupsService.getGroupById(groupId).get();
    }


    @MessageMapping("/delete_group")
    public void deleteGroup(SimpMessageHeaderAccessor sha, @Payload String inputGroupId){
        UUID userId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID groupId=UUID.fromString(inputGroupId);
        Optional<GroupModel> groupModel=groupsService.getGroupById(groupId);
        if(groupModel.isPresent() && groupModel.get().getUserId().equals(userId)){
            notificationService.adminDeletedGroupNotification(groupId);
            groupsService.deleteGroup(groupId);
        }
    }

    @MessageMapping("/get_groups")
    @SendToUser("/queue/user_groups")
    public List<GroupModel> getUserGroups(SimpMessageHeaderAccessor sha) {
        UUID userId=userService.getByUsername(sha.getUser().getName()).get().getId();
        return groupsService.getUserGroups(userId);
    }

    @MessageMapping("/get_group_details")
    public void getGroupDetails(SimpMessageHeaderAccessor sha, @Payload String groupIdInput){
        UUID userId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID groupId=UUID.fromString(groupIdInput);
        Optional<PublicUserModel> groupOwner=groupsService.getGroupOwner(groupId);
        List<PublicUserModel> groupMembers=groupsService.getGroupMembers(groupId);
        if(groupOwner.isPresent() && !groupMembers.isEmpty()){
            GroupDetailsOutputDto groupDetailsOutputDto=GroupDetailsOutputDto.build().setMembers(groupMembers).setOwner(groupOwner.get());
            simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/group_details/"+groupIdInput, groupDetailsOutputDto);
        }
    }
    @MessageMapping("/leave_group")
    public void leaveGroup(SimpMessageHeaderAccessor sha, @Payload String groupIdInput){
        UUID userId=userService.getByUsername(sha.getUser().getName()).get().getId();
        UUID groupId=UUID.fromString(groupIdInput);
        notificationService.userLeavedGroupNotification(groupId,userId);
        groupsService.leaveGroup(groupId,userId);
    }

    //de facut update si pe grupul de pe client
    @MessageMapping("/update_group")
    public void updateGroup(SimpMessageHeaderAccessor sha, @Payload UpdateGroupInputDto input){
        UUID groupId=UUID.fromString(input.groupId);
        List<UUID> groupMembersIds=new LinkedList<>();
        for(String id:input.groupMembersIds){
            groupMembersIds.add(UUID.fromString(id));
        }
        groupsService.updateGroup(groupId,input.groupName,groupMembersIds);
        System.out.println("group has been updated --------------------------------------------------------------------------------");
    }

}
