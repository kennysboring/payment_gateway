package com.github.kenedy.paymentgateway.controllers;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        Optional<User> user = repository.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<User> findAll() {
        return repository.findAll();
    }
}
