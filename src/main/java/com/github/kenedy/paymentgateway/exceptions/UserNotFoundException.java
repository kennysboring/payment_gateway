package com.github.kenedy.paymentgateway.exceptions;

public class UserNotFoundException extends DomainException{

    public UserNotFoundException(String message) {
        super(message);
    }
    
}
