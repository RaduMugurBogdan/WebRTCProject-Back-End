package com.example.AccountAPI.service;

import com.example.AccountAPI.exception.UserAccountExceptions.InvalidAccountDataException;
import com.example.AccountAPI.model.PublicUserModel;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.util.AccountCreationFailureDetails;
import com.example.AccountAPI.util.AccountDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {
    @Autowired
    private AccountDataValidator dataValidator;
    @Autowired
    private UsersRepositoryInterface usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UUID> createUser(UserModel user) {

        Optional<AccountCreationFailureDetails> errorData = dataValidator.checkUserDataValidity(user);
        if (errorData.isPresent()) {
            throw new InvalidAccountDataException(errorData.get());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.create(user);
    }

    public Optional<UserModel> getByUsername(String username) {
        return usersRepository.getByUsername(username);
    }

    @Override
    public List<PublicUserModel> getUsersLike(String like) {
        return usersRepository.getUsersLike(like);
    }

    public List<UserModel> getAllUsers() {
        return usersRepository.getAll();
    }

}
