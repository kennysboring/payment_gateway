package com.github.kenedy.paymentgateway;
import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;

public class Main {
    public static void main(String[] args) {

        Usuario testUser = new Usuario(
            1, 
            "000.000.000-01",
            "person", 
            "person@email.com", 
            new BigDecimal("100.25"), 
            Usuario.TipoUsuario.COMUM);

        try {
            testUser.debitar(new BigDecimal("50.00"));

        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());

        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }

        try {
            testUser.creditar(new BigDecimal("50.00"));

        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }

    }
}
