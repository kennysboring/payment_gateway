package com.github.kenedy.paymentgateway.services;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.kenedy.paymentgateway.domain.Transfer;
import com.github.kenedy.paymentgateway.domain.User;
import com.github.kenedy.paymentgateway.exceptions.InsufficientBalanceException;
import com.github.kenedy.paymentgateway.exceptions.IllegalValueException;
import com.github.kenedy.paymentgateway.exceptions.MerchantCannotPayException;
import com.github.kenedy.paymentgateway.exceptions.SelfTransferException;
import com.github.kenedy.paymentgateway.repositories.InMemoryTransactionRepository;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;


public class TransferServiceTest {
    TransactionRepository repository = new InMemoryTransactionRepository();
    TransferService service = new TransferService(repository);

    @Test 
    void shouldExecuteCorrectly() {
        User payer = new User("000.000.000-01", 
            "payer", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.COMMON);

        User payee = new User("000.000.000-02", 
            "payee", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.COMMON);

        Transfer transfer = new Transfer(payer, new BigDecimal("100"), payee);
        service.execute(transfer);

        Assertions.assertEquals(Transfer.TransactionStatus.COMPLETED, transfer.getStatus());
        Assertions.assertTrue(new BigDecimal("0.99").compareTo(payer.getBalance()) == 0); 
        Assertions.assertTrue(new BigDecimal("200.99").compareTo(payee.getBalance()) == 0); 
        Assertions.assertTrue(repository.findById(transfer.getId()).isPresent()); 

    }

    @Test
    void shouldThrowExceptionWhenTransferIsNull() {
        Assertions.assertThrows(IllegalValueException.class, () -> {
            service.execute(null);
        });
    }

    @Test
    void shouldThrowExceptionWhenMerchantTryingTransfer() {
        User payer = new User("000.000.000-01", 
            "payer", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.MERCHANT);

        User payee = new User("000.000.000-02", 
            "payee", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.COMMON);

        Transfer transfer = new Transfer(payer, new BigDecimal("100"), payee);

        Assertions.assertThrows(MerchantCannotPayException.class, () -> {
            service.execute(transfer);
        });
        Assertions.assertEquals(Transfer.TransactionStatus.FAILED, transfer.getStatus());
        Assertions.assertTrue(new BigDecimal("100.99").compareTo(payer.getBalance()) == 0);
    }

    @Test
    void shouldThrowExceptionWhenDoingSelfTransaction() {
        User payer = new User("000.000.000-01", 
            "payer", 
            "person@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.COMMON);

        Transfer transfer = new Transfer(payer, new BigDecimal("100"), payer);

        Assertions.assertThrows(SelfTransferException.class, () -> {
            service.execute(transfer);
        });
    }

    @Test
    void shouldMarkAsFailedWhenPayerHasInsufficientBalance() {
        User payer = new User("000.000.000-01", 
            "payer", 
            "person1@email.com", 
            new BigDecimal("10"), 
            User.UserType.COMMON);

        User payee = new User("000.000.000-02", 
            "payee", 
            "person2@email.com", 
            new BigDecimal("100.99"), 
            User.UserType.COMMON);

        Transfer transfer = new Transfer(payer, new BigDecimal("100"), payee);

        Assertions.assertThrows(InsufficientBalanceException.class, () -> {
            service.execute(transfer);
        });
        Assertions.assertEquals(Transfer.TransactionStatus.FAILED, transfer.getStatus());
        Assertions.assertTrue(repository.findById(transfer.getId()).isPresent());
    }
}   
