package com.github.kenedy.paymentgateway.domain;

import java.math.BigDecimal;
import java.util.UUID;

import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Transfer {
    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
    }

    @Id   
    private UUID id;

    @ManyToOne 
    @JoinColumn(name = "payer_id", nullable = false)
    private User payer; 

    @ManyToOne 
    @JoinColumn(name = "payee_id", nullable = false)
    private User payee;
    private BigDecimal amount; 
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    protected Transfer() {}

    public Transfer(User payer, BigDecimal amount, User payee) {
        if (payer == null || payee == null) {
            throw new IllegalValueException("ERROR: payer or payee not defined");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR: value is not accepted");
        }

        this.id = UUID.randomUUID();
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.status = TransactionStatus.PENDING;
        }
    
    public UUID getId() { return id; }
    public User getPayer() { return payer; }
    public User getPayee() { return payee; }
    public BigDecimal getAmount() { return amount; }
    public TransactionStatus getStatus() { return status; }

    public void setStatusAsCompleted() { this.status = TransactionStatus.COMPLETED; }
    public void setStatusAsFailed() { this.status = TransactionStatus.FAILED; }
    }

