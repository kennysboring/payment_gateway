package com.github.kenedy.paymentgateway.repositories;

import java.util.List;
import java.util.Optional;

import com.github.kenedy.paymentgateway.domain.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(long id);
    List<User> findAll();
}
