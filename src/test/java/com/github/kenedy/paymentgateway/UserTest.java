package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.kenedy.paymentgateway.domain.User;
import com.github.kenedy.paymentgateway.domain.User.UserType;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;

public class UserTest {
    
    @Test
    void shouldCreateValidUser() {
        User user = new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        Assertions.assertEquals("000.000.000-01", user.getCpf());
        Assertions.assertEquals("person@email.com", user.getEmail());
        Assertions.assertEquals(new BigDecimal("100.99"), user.getBalance());
        Assertions.assertEquals(UserType.COMMON, user.getUserType());

    }

    @Test
    void shouldSetBalanceToZeroWhenDebitingExactBalanceAmount() {
        User user = new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("100.0"), 
            UserType.COMMON);

        user.debit(new BigDecimal("100"));
        Assertions.assertTrue(BigDecimal.ZERO.compareTo(user.getBalance()) == 0);
    }

    @Test
    void shouldCreateUserWithZeroBalance() {
        User user = new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("0"), 
            UserType.COMMON);

        Assertions.assertTrue(BigDecimal.ZERO.compareTo(user.getBalance()) == 0);
    }

    @Test 
    void shouldThrowExceptionWhenCpfIsBlank(){
        Assertions.assertThrows(IllegalValueException.class, () -> {
            new User("", //Blank
            "person", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);
        });
    }

    @Test 
    void shouldThrowExceptionWhenNameIsBlank(){
        Assertions.assertThrows(IllegalValueException.class, () -> {
            new User("000.000.000-01", 
            "",  //Blank
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);
        });
    }

    @Test 
    void shouldThrowExceptionWhenEmailIsInvalid(){
        Assertions.assertThrows(IllegalValueException.class, () -> {
            new User("000.000.000-01", 
            "person",
            "email",  //Invalid
            new BigDecimal("100.99"), 
            UserType.COMMON);
        });
    }
    
    @Test 
    void shouldThrowExceptionWhenBalanceIsNegative() {
        Assertions.assertThrows(IllegalValueException.class, () -> {
            new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("-1.0"), //Negative
            UserType.COMMON);
        });
    }

    @Test 
    void shouldThrowExceptionWhenUserTypeIsNull() {
        Assertions.assertThrows(IllegalValueException.class, () -> {
            new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            null); //Null
        });
    }

    @Test
    void shouldCorrectDebit() {
        User user = new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        user.debit(new BigDecimal("0.99"));

        Assertions.assertTrue(new BigDecimal("100").compareTo(user.getBalance()) == 0); //.compareTo() return '0' when comparation is true
    }

    @Test 
    void shouldThrowExceptionWhenAmountIsNegativeToDebit() {
        Assertions.assertThrows(IllegalValueException.class, () -> {
            User user = new User("000.000.000-01", 
                "person", 
                "person@email.com", 
                new BigDecimal("100.99"), 
                UserType.COMMON);

            user.debit(new BigDecimal("-1"));
        });
    }

    @Test 
    void shouldThrowExceptionWhenUserHaveNoBalanceEnoughToDebit() {
        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            User user = new User("000.000.000-01", 
                "person", 
                "person@email.com", 
                new BigDecimal("100"), 
                UserType.COMMON);
            
            user.debit(new BigDecimal("200"));
        });
    }

    @Test
    void shouldCorrectCredit() {
        User user = new User("000.000.000-01", 
            "person", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            UserType.COMMON);

        user.credit(new BigDecimal("0.01"));

        Assertions.assertTrue(new BigDecimal("101").compareTo(user.getBalance()) == 0); //.compareTo() return '0' when comparation is true
    }

    @Test 
    void shouldThrowExceptionWhenAmountIsNegativeToCredit() {
        Assertions.assertThrows(IllegalValueException.class, () -> {
            User user = new User("000.000.000-01", 
                "person", 
                "person@email.com", 
                new BigDecimal("100.99"), 
                UserType.COMMON);

            user.credit(new BigDecimal("-1"));
        });
    }
}
