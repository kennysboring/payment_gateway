package com.github.kenedy.paymentgateway.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kenedy.paymentgateway.domain.User;
import com.github.kenedy.paymentgateway.dto.CreateUserRequest;
import com.github.kenedy.paymentgateway.repositories.UserRepository;

@RestController 
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public User create(@RequestBody CreateUserRequest request) {
        User user = new User(
            request.getCpf(),
            request.getName(),
            request.getEmail(),
            request.getBalance(),
            request.getUserType());

        repository.save(user);
        return user;
    } 
}
