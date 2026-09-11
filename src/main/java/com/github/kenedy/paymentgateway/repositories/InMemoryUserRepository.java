package com.github.kenedy.paymentgateway.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.kenedy.paymentgateway.domain.User;

@Repository 
public class InMemoryUserRepository implements UserRepository{

    private final Map<Long, User> repository = new HashMap<>(); 

    @Override
    public void save(User user) {
        repository.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(repository.values());
    } 
    
}
