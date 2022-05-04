package com.example.AccountAPI.api;


import com.example.AccountAPI.dto.input_dtos.CreateUserInputDto;
import com.example.AccountAPI.dto.output_dtos.CreateUserOutputDto;
import com.example.AccountAPI.dto.output_dtos.GetUserOutputDto;
import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceInterface userService;

    @PostMapping("/user")
    public ResponseEntity<CreateUserOutputDto> createUser(@RequestBody CreateUserInputDto user) {
        Optional<UUID> userId = userService.createUser(new UserModel(user.username, user.firstName, user.lastName, user.email, user.password));
        if (userId.isPresent()) {
            return ResponseEntity.ok().body(new CreateUserOutputDto(userId.get()));
        }
        return ResponseEntity.badRequest().body(new CreateUserOutputDto(UUID.randomUUID()));

    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<List<GetUserOutputDto>> getUsers(Principal principal) {
        List<UserModel> users = userService.getAllUsers();
        List<GetUserOutputDto> usersOutput = new LinkedList<>();
        for (UserModel user : users) {
            usersOutput.add(new GetUserOutputDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail()));
        }
        return ResponseEntity.ok().body(usersOutput);
    }

}
