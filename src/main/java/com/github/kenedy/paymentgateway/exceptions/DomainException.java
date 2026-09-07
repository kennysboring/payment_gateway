package com.github.kenedy.paymentgateway.exceptions;

public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }
}
