package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;

public class User {
    public enum UserType {
        COMMON,
        MERCHANT,
    }

    private final long id;
    private String name;
    private final String cpf;
    private String email;
    private BigDecimal balance;
    private UserType userType;

    public User( long id,
        String cpf,
        String name, 
        String email, 
        BigDecimal balance, 
        UserType userType) 
    {
        if (id <= 0) {
            throw new IllegalValueException("ERROR: id cannot be less than 0(zero)");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalValueException("ERROR: cpf is null");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalValueException("ERROR: name cannot be blank");
        }

        if (email == null || !email.contains("@")) {
            throw new IllegalValueException("ERROR: invalid email");
        }

        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalValueException("ERROR: balance cannot be less than 0(zero)");
        }

        if (userType == null) {
            throw new IllegalValueException("ERROR: select a user valid type");
        }
        
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.userType = userType;
        
    }

    public void debit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to debit: value is not accepted");
        }
        
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException(amount, this.balance);
        }

        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalValueException("ERROR to credit: value is not accepted");
        }
        
        this.balance = this.balance.add(amount);
    }

    //all getters
    public long getId(){ return id; }
    public String getCpf(){ return cpf; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public BigDecimal getBalance(){ return balance; }
    public UserType getUserType(){ return userType; }
}