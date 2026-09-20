package com.github.kenedy.paymentgateway.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.github.kenedy.paymentgateway.domain.User;

public class UserResponse {
    private final UUID id;
    private final String cpf;
    private final String name;
    private final String email;
    private final BigDecimal balance;
    private final User.UserType userType;

    public UserResponse(User user) {
        this.id = user.getId();
        this.cpf = user.getCpf();
        this.name = user.getName();
        this.email = user.getEmail();
        this.balance = user.getBalance();
        this.userType = user.getUserType();
    }

    public UUID getId() { return id; }
    public String getCpf() { return cpf; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getBalance() { return balance; }
    public User.UserType getUserType() { return userType; }
}
