package com.github.kenedy.paymentgateway.exceptions;

public class MerchantCannotPayException extends DomainException{
    public MerchantCannotPayException() {
        super("ERROR: MERCHANT is not allowed to do a payment");
    }
}
