package com.github.kenedy.paymentgateway.dto;

import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.domain.User;

public class CreateUserRequest {
    private String cpf;
    private String name;
    private String email;
    private BigDecimal balance;
    private User.UserType userType;

    //getters
    public String getCpf() { return cpf; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public BigDecimal getBalance() { return balance; }
    public User.UserType getUserType() { return userType; }

    //setters
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setUserType(User.UserType userType) { this.userType = userType; }

    
}
