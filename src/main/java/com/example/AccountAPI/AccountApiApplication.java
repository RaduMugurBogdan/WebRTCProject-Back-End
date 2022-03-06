package com.example.AccountAPI;

import com.example.AccountAPI.model.UserModel;
import com.example.AccountAPI.repository.UserRepository;
import com.example.AccountAPI.repository.interfaces.BasicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class AccountApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApiApplication.class, args);
	}
}
