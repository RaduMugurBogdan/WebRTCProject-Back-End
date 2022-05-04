package com.example.AccountAPI.config;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.interfaces.UsersRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserCredentials implements UserDetailsService {
    @Autowired
    private UsersRepositoryInterface usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = usersRepository.getByUsername(username);
        if (userModel.isPresent()) {
            return new UserData(userModel.get());
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
