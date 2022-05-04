package com.example.AccountAPI.service;

import com.example.AccountAPI.model.UserStatusModel;
import com.example.AccountAPI.repository.enums.UserStatusValue;
import com.example.AccountAPI.repository.interfaces.UserStatusRepositoryInterface;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import com.example.AccountAPI.service.interfaces.UserStatusServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class UserStatusService implements UserStatusServiceInterface {
    @Autowired
    private UserStatusRepositoryInterface userStatusRepository;
    @Autowired
    private UsersRepositoryInterface usersRepository;
    private Integer awayTimeLimit=10*1000;//10 minutes

    @Override
    public UserStatusValue getUserStatus(UUID userId) {
        Optional<UserStatusModel> userStatus=userStatusRepository.getUserStatus(userId);
        if(!userStatus.isPresent()){
            return UserStatusValue.OFFLINE;
        }
        UserStatusModel userStatusModel=userStatus.get();
        if(userStatusModel.getUserStatus().equals(UserStatusValue.OFFLINE) || userStatusModel.getUserStatus().equals(UserStatusValue.IN_A_CALL) || userStatusModel.getUserStatus().equals((UserStatusValue.AWAY))){
            return userStatusModel.getUserStatus();
        }

        Date date=new Date();
        if(TimeUnit.MINUTES.convert(date.getTime()-userStatus.get().getLastAccessDate().getTime(),TimeUnit.MILLISECONDS)>awayTimeLimit){
            return UserStatusValue.AWAY;
        }else{
            return userStatusModel.getUserStatus();
        }
    }

    @Override
    public void setUserStatus(UUID userId, UserStatusValue userStatusValue) {
        Optional<UserStatusModel> userStatusModel=userStatusRepository.getUserStatus(userId);
        if(!userStatusModel.isPresent()){
            userStatusRepository.createUserStatus(new UserStatusModel()
                    .setId(UUID.randomUUID())
                    .setUserId(userId)
                    .setUserStatus(userStatusValue)
                    .setLastAccessDate(new Date())
            );
        }else{
            UserStatusModel searchResult=userStatusModel.get();
            searchResult.setUserStatus(userStatusValue);
            searchResult.setLastAccessDate(new Date());
            userStatusRepository.updateUserStatus(searchResult);
        }
    }
    @Override
    public void updateUserLastAccessTime(UUID userId) {
        Optional<UserStatusModel> userStatusModel=userStatusRepository.getUserStatus(userId);
        if(userStatusModel.isPresent()){
            UserStatusModel searchResult=userStatusModel.get();
            searchResult.setLastAccessDate(new Date());
            userStatusRepository.updateUserStatus(searchResult);
        }
    }

    @Override
    public void deleteUserStatus(UUID userId) {
        this.userStatusRepository.deleteUserStatus(userId);
    }
}
