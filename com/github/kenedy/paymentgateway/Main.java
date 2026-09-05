package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;

public class Main {
    public static void main(String[] args) {

        User testUser = new User(
            1, 
            "000.000.000-01",
            "person", 
            "person@email.com", 
            new BigDecimal("100.25"), 
            User.UserType.COMMON);

        try {
            testUser.debit(new BigDecimal("50.00"));

        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());

        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }

        try {
            testUser.credit(new BigDecimal("50.00"));

        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }
    }
}
