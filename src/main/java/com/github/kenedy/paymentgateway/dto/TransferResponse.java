package com.github.kenedy.paymentgateway.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.github.kenedy.paymentgateway.domain.Transfer;

public class TransferResponse {
    private final UUID id;
    private final UserResponse payer;
    private final UserResponse payee;
    private final BigDecimal amount;
    private final Transfer.TransactionStatus status;

    public TransferResponse(Transfer transfer) {
        this.id = transfer.getId();
        this.payer = new UserResponse(transfer.getPayer());
        this.payee = new UserResponse(transfer.getPayee());
        this.amount = transfer.getAmount();
        this.status = transfer.getStatus();
    }

    public UUID getId() { return id; }
    public UserResponse getPayer() { return payer; }
    public UserResponse getPayee() { return payee; }
    public BigDecimal getAmount() { return amount; }
    public Transfer.TransactionStatus getStatus() { return status; }
}