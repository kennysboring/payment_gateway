package src;
import java.math.BigDecimal;

import src.exceptions.IllegalValueException;
import src.exceptions.InsufficientBalanceException;

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
            testUser.debitar(new BigDecimal("50.25"));
            testUser.creditar(new BigDecimal("30"));

        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
            
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }

    }
}
