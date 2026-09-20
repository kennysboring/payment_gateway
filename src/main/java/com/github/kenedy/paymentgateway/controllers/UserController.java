package com.github.kenedy.paymentgateway.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kenedy.paymentgateway.domain.User;
import com.github.kenedy.paymentgateway.dto.CreateUserRequest;
import com.github.kenedy.paymentgateway.dto.UserResponse;
import com.github.kenedy.paymentgateway.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        User user = new User(
            request.getCpf(),
            request.getName(),
            request.getEmail(),
            request.getBalance(),
            request.getUserType());

        repository.save(user);
        return new UserResponse(user);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return repository.findById(id)
            .map(user -> ResponseEntity.ok(new UserResponse(user)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return repository.findAll().stream()
            .map(UserResponse::new)
            .toList();
    }
}
