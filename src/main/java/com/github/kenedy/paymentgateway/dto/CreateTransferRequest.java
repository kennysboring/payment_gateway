package com.github.kenedy.paymentgateway.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateTransferRequest {

    @NotNull
    private UUID payeeId;

    @NotNull
    private UUID payerId;
    
    @NotNull
    @Positive
    private BigDecimal amount;

    //all getters
    public UUID getPayeeId() { return payeeId; }
    public UUID getPayerId() { return payerId; }
    public BigDecimal getAmount() { return amount; }

    //all setter
    public void setPayeeId(UUID payeeId) { this.payeeId = payeeId; }
    public void setPayerId(UUID payerId) { this.payerId = payerId; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
