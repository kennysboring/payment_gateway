package com.github.kenedy.paymentgateway.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.github.kenedy.paymentgateway.domain.Transfer;

@Repository 
public class InMemoryTransactionRepository implements TransactionRepository{

    private final Map<UUID, Transfer> transfers = new HashMap<>();

    @Override 
    public void save(Transfer transfer) {
        transfers.put(transfer.getId(), transfer);
    }

    @Override 
    public Optional<Transfer> findById(UUID id) {
        return Optional.ofNullable(transfers.get(id));
    }

    @Override 
    public List<Transfer> findAll() {
        return new ArrayList<Transfer>(transfers.values()) ;
    }
    
}
