package com.example.AccountAPI.api;


import com.example.AccountAPI.dto.input_dtos.UserLoginDto;
import com.example.AccountAPI.exception.UserAccountExceptions.InvalidLoginDataException;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import com.example.AccountAPI.util.AccountDataValidator;
import com.example.AccountAPI.util.JwtUtils;
import com.example.AccountAPI.util.LoginFailureDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
/*
    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto){
        return null;

    }*/

    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private JwtUtils jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountDataValidator accountDataValidator;


    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody UserLoginDto dto){
        Optional<LoginFailureDetails> loginFailureDetails=accountDataValidator.checkUserLoginDataValidity(dto);
        if(loginFailureDetails.isPresent()){
            throw new InvalidLoginDataException(loginFailureDetails.get());
        }
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        authManager.authenticate(authInputToken);
        String token = jwtUtil.generateToken(dto.getUsername());
        return Collections.singletonMap("jwt-token", token);
    }

}
