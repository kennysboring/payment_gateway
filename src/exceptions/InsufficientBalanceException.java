package src.exceptions;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(BigDecimal value, BigDecimal balance) {
        super(String.format("User do not have money enough to pay. value: %s | balance: %s", value, balance));
    }
}