package com.github.kenedy.paymentgateway.exceptions;

public class SelfTransferException extends DomainException {
    public SelfTransferException() {
        super("ERROR: you cannot do a transaction to yourself");
    }
}
