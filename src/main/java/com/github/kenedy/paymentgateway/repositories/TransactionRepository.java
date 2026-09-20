package com.github.kenedy.paymentgateway.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.kenedy.paymentgateway.domain.Transfer;

public interface TransactionRepository extends JpaRepository<Transfer, UUID> {}
