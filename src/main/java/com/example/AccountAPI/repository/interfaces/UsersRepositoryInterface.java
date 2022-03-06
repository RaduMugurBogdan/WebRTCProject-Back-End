package com.example.AccountAPI.repository.interfaces;

import com.example.AccountAPI.model.UserModel;

import java.util.Optional;

public interface UsersRepositoryInterface extends BasicRepository<UserModel>{
    boolean findByEmail(String email);

}
