package com.github.kenedy.paymentgateway.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.kenedy.paymentgateway.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse body = new ErrorResponse(404, "Not Found", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({
        IllegalValueException.class,
        InsufficientBalanceException.class,
        MerchantCannotPayException.class,
        SelfTransferException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(DomainException e) {
        ErrorResponse body = new ErrorResponse(400, "Bad Request", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}