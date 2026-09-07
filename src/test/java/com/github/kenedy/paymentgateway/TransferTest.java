package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.kenedy.paymentgateway.User.UserType;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;

public class TransferTest {

    @Test 
    void shouldThrowExceptionWhenPayerIsNull() {
        User payee = new User(1, 
            "000.000.000-01", 
            "payee", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        Assertions.assertThrows(IllegalValueException.class, () -> {
            new Transfer(null, new BigDecimal("100"), payee);
        });
    }

    @Test 
    void shouldThrowExceptionWhenPayeeIsNull() {
        User payer = new User(1, 
            "000.000.000-01", 
            "payee", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        Assertions.assertThrows(IllegalValueException.class, () -> {
            new Transfer(payer, new BigDecimal("100"), null);
        });
    }
    
    @Test 
    void shouldThrowExceptionWhenAmountIsNegativeToTransfer() {
        User payer = new User(1, 
            "000.000.000-01", 
            "payee", 
            "person1@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        User payee = new User(2, 
            "000.000.000-02", 
            "payer", 
            "person2@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        Assertions.assertThrows(IllegalValueException.class, () -> {
            new Transfer(payer, new BigDecimal("-100"), payee);   
        });
    }
}
