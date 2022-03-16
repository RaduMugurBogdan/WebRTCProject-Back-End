package com.example.AccountAPI.service;

import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.repository.interfaces.FriendsRepositoryInterface;
import com.example.AccountAPI.service.interfaces.FriendsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FriendsService implements FriendsServiceInterface {
    @Autowired
    private FriendsRepositoryInterface friendsRepository;

    public List<PublicUserModel> getAllFriends(UUID userId){
        return friendsRepository.getAllFriends(userId);
    }


    public List<PublicUserModel> findUserByName(UUID userId,String userName){
        return null;
    }


    public Optional<UUID> createFriendRelation(UUID currentUserId, UUID friendUserId){
        if(friendsRepository.exists(currentUserId,friendUserId)){
            return Optional.empty();
        }
        return Optional.of(friendsRepository.create(currentUserId,friendUserId));
    }

    public void deleteFriendRelation(UUID currentUserId, UUID friendUserId){
        friendsRepository.delete(currentUserId, friendUserId);
    }

}
