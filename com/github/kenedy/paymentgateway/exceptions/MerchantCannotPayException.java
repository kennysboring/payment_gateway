package com.github.kenedy.paymentgateway.exceptions;

public class MerchantCannotPayException extends DomainException{
    public MerchantCannotPayException() {
        super("ERROR: LOJISTA is not allowed to do a payment");
    }
}
