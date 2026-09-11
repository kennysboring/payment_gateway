package com.github.kenedy.paymentgateway.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateTransferRequest {
    private UUID payeeId;
    private UUID payerId;
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
