package com.github.kenedy.paymentgateway.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.github.kenedy.paymentgateway.domain.Transfer;

public interface TransactionRepository {
    void save(Transfer transfer);
    Optional<Transfer> findById(UUID id);
    List<Transfer> findAll();
}
