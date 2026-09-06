package com.github.kenedy.paymentgateway.services;

import com.github.kenedy.paymentgateway.Transfer;
import com.github.kenedy.paymentgateway.User.UserType;
import com.github.kenedy.paymentgateway.exceptions.DomainException;
import com.github.kenedy.paymentgateway.exceptions.MerchantCannotPayException;
import com.github.kenedy.paymentgateway.exceptions.SelfTransferException;
import com.github.kenedy.paymentgateway.repositories.TransactionRepository;

public class TransferService {
    private final TransactionRepository repository;

    public TransferService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void execute(Transfer t) {
        try{
            if (t.getPayer().getUserType() == UserType.MERCHANT) {
                throw new MerchantCannotPayException();
            }
            
            if (t.getPayer().getId() == t.getPayee().getId()) {
                throw new SelfTransferException();
            }

            t.getPayer().debit(t.getAmount());
            t.getPayee().credit(t.getAmount());
            t.setStatusAsCompleted();

        } catch (DomainException e) {
            t.setStatusAsFailed();
            throw e;

        } finally {
            repository.save(t);
        }
    }
}
