package com.github.kenedy.paymentgateway.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kenedy.paymentgateway.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {}
