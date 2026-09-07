package com.github.kenedy.paymentgateway;

import java.math.BigDecimal;

import com.github.kenedy.paymentgateway.exceptions.DomainException;
import com.github.kenedy.paymentgateway.repositories.InMemoryTransactionRepository;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;
import com.github.kenedy.paymentgateway.services.TransferService;

public class Main {
    public static void main(String[] args) {
        TransactionRepository repository = new InMemoryTransactionRepository();
        TransferService service = new TransferService(repository);

        User testUser1 = new User(
            1,
            "000.000.000-01",
            "person 1", 
            "persontwo@email.com", 
            new BigDecimal("150.25"), 
            User.UserType.COMMON);

        User testUser2 = new User(
            2,
            "000.000.000-02",
            "person 2", 
            "personone@email.com", 
            new BigDecimal("80.80"), 
            User.UserType.MERCHANT);


        //Test transaction: COMMON user to MERCHANT user
        Transfer transaction1 = new Transfer(testUser1, new BigDecimal("0.25"), testUser2);
        try {
            service.execute(transaction1);

        } catch (DomainException e) {
            System.out.println(e.getMessage());

        } finally {
            System.out.println("Transaction finished");
            System.out.println();
        }

        //Test repository: trying get a transaction with id
        try {
            System.out.println("Transaction: " + repository.findById(transaction1.getId()));

        } catch (DomainException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

        //Test repository: trying get all transactions
        try {
            System.out.println("All Transaction: " + repository.findAll());

        } catch (DomainException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
        
        //Test failed transaction: MERCHANT user to COMMON user
        Transfer transaction2 = new Transfer(testUser2, new BigDecimal("0.25"), testUser1);
        try {
            service.execute(transaction2);

        } catch (DomainException e) {
            System.out.println(e.getMessage());

        } finally {
            System.out.println("Transaction 2 finished");
            System.out.println("Balance: " + testUser2.getBalance());
            System.out.println();
        }

        //Test failed transaction: amount with no value
        try {
            Transfer transaction3 = new Transfer(testUser1, new BigDecimal("0"), testUser2);
            service.execute(transaction3);

        } catch (DomainException e) {
            System.out.println(e.getMessage());

        } finally {
            System.out.println("Transaction 3 finished");
            System.out.println();
        }

        //Test failed transaction:  user to same user
        try {
            Transfer transaction4 = new Transfer(testUser1, new BigDecimal("50"), testUser1);
            service.execute(transaction4);

        } catch (DomainException e) {
            System.out.println(e.getMessage());

        } finally {
            System.out.println("Transaction 4 finished");
            System.out.println();
        }
    }
}
