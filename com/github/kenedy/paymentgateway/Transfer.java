package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;

public class Transfer {
    private enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
    }

    private User payer; 
    private User payee;
    private BigDecimal amount; 
    private TransactionStatus status;

    public Transfer(User payer, BigDecimal amount, User payee) {
        if (payer == null || payee == null) {
            throw new IllegalValueException("ERROR: payer or payee not defined");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR: value is not accepted");
        }

        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
        }
    

    public User getPayer() { return payer; }
    public User getPayee() { return payee; }
    public BigDecimal getAmount() { return amount; }

    public void setStatusAsCompleted() { this.status = TransactionStatus.COMPLETED; }
    public void setStatusAsFailed() { this.status = TransactionStatus.FAILED; }
    }

