package com.example.AccountAPI.sockets_dtos;

import com.example.AccountAPI.model.PublicUserModel;

import java.util.List;

public class GroupDetailsOutputDto {
    private PublicUserModel owner;
    private List<PublicUserModel> members;

    public static GroupDetailsOutputDto build(){
        return new GroupDetailsOutputDto();
    }

    public PublicUserModel getOwnerId() {
        return owner;
    }

    public GroupDetailsOutputDto setOwner(PublicUserModel owner) {
        this.owner = owner;
        return this;
    }

    public List<PublicUserModel> getMembers() {
        return members;
    }

    public GroupDetailsOutputDto setMembers(List<PublicUserModel> members) {
        this.members = members;
        return this;
    }
}
